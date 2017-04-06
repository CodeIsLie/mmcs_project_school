package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sun.reflect.generics.tree.Tree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.io.*;
import java.util.HashMap;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        /*primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();*/
        SchoolTasks mainFrame = new SchoolTasks();
        mainFrame.go();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public class SchoolTasks {
        JTextArea TestText;
        ArrayList<String> names;

        //панели в левой части с заданиями
        HashMap<JPanel, Example> panelMap;
        ArrayList<JPanel> listPanel;

        JFrame frameConstructor;
        JFrame frameTasks;
        JPanel leftPanel;
        JPanel rightPanel;
        JPanel panelTasks;
        TextField numberArea;

        int maxPanels = 10;
        int compY = 30;
        int butLength = 45;
        int labelLength = 250;
        int space = 20;
        int maxHeight;

        public void go() {
            //все типы задач
            String[] typesOfTaskes = {"Десятичные дроби", "Положит. и отриц. числа", "Неравенство",
                    "Линейное уравнение"};
            names = new ArrayList<String>();
            for (int i = 0; i < typesOfTaskes.length; ++i)
                names.add(typesOfTaskes[i]);

            leftInitialize();
            rightInitialize();
        }

        private void leftInitialize(){
            //левая панель
            panelMap = new HashMap<JPanel, Example>();
            listPanel = new ArrayList<JPanel>();
            leftPanel = new JPanel();
            leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
            //панель - заголовок
            JPanel header1 = new JPanel();
            header1.setLayout(new BoxLayout(header1, BoxLayout.X_AXIS));

            //Font font = new Font("Verdana", Font.PLAIN, 11);
            JLabel labelHead = new JLabel("     Список заданий");
            labelHead.setPreferredSize(new Dimension(labelLength ,50));
            labelHead.setForeground(Color.white);

            JLabel diff = new JLabel("сложность  ");
            diff.setForeground(Color.white);
            diff.setAlignmentX(Component.RIGHT_ALIGNMENT);

            header1.add(labelHead);
            header1.add(diff);
            header1.setMinimumSize(new Dimension(labelLength + space + butLength, 60));
            header1.setMaximumSize(new Dimension(labelLength + space + butLength + 80, 60));
            //темно синий цвет
            header1.setBackground(new Color(0x133195));
            header1.setAlignmentX(Component.LEFT_ALIGNMENT);

            leftPanel.add(header1);

            maxHeight = 50 + maxPanels * (compY + 10);
            leftPanel.setMinimumSize(new Dimension(300, maxHeight));
            leftPanel.setPreferredSize(new Dimension(300, maxHeight));

            JPanel backgroundPanel = new JPanel(){
                private String background = "/images/background1.jpg";
                private URL imgFile = Main.class.getResource(background);

                public void changePicture(String imgFile) {
                    this.imgFile = Main.class.getResource("/images/" + imgFile);
                    repaint();
                }
                public void paint(Graphics g) {
                    super.paint(g);
                    try {
                        Image img = new ImageIcon(Main.class.getResource(background)).getImage();
                        g.drawImage(img, 0, 0, null);
                    } catch (Exception ex) {
                        //Logger.getLogger(JFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };

            JLayeredPane layeredPane = new JLayeredPane();
            layeredPane.setPreferredSize(new Dimension(310, maxHeight));
            layeredPane.add(backgroundPanel, new Integer(0));
            layeredPane.add(leftPanel, new Integer(1));

            leftPanel.setLocation(0,0);
            leftPanel.setBounds(0, 0, 310, maxHeight + 10);
            leftPanel.setOpaque(false);
            backgroundPanel.setLocation(0,0);
            backgroundPanel.setBounds(0, 0, 310, maxHeight);

            frameTasks = new JFrame();
            frameTasks.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frameTasks.getContentPane().setLayout(new BorderLayout());
            frameTasks.getContentPane().add(BorderLayout.CENTER, layeredPane);
            //frameTasks.setUndecorated(true);
            //frameTasks.pack();
            frameTasks.setSize(316, maxHeight + 29);
            frameTasks.setResizable(false);
            frameTasks.setVisible(true);
        }

        //инициализация правого фрейма(конструктора)
        private void rightInitialize(){
            frameConstructor = new JFrame();

            rightPanel = new JPanel();
            rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
            JPanel backgroundPanel = new JPanel() {
                private String background = "/images/background1.jpg";
                //private URL imgFile = Main.class.getResource(background);
                /*public void changePicture(String imgFile) {
                    this.imgFile = Main.class.getResource("/images/" + imgFile);
                    repaint();
                }*/
                public void paint(Graphics g) {
                    super.paint(g);
                    try {
                        Image img = new ImageIcon(Main.class.getResource(background)).getImage();
                        g.drawImage(img, 0, 0, null);
                    } catch (Exception ex) {
                        //Logger.getLogger(JFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
            backgroundPanel.setPreferredSize(new Dimension(460, 360));

            //панель с заданиями
            panelTasks = new JPanel();
            panelTasks.setLayout(new GridBagLayout());
            panelTasks.setMaximumSize(new Dimension(400, 500));
            panelTasks.setOpaque(false);
            ArrayList<Triple> triples = new ArrayList<Triple>();

            GridBagConstraints c = new GridBagConstraints();
            c.gridx = 2;
            //JLabel label = new JLabel("виды заданий");
            //panelTasks.add(label, c);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 4;
            /*JLabel lab = new JLabel("сложность");
            lab.setForeground(new Color(0xFFFFFF));
            panelTasks.add(lab, c);*/

            //pазделитель
            Dimension minSize1 = new Dimension(20, 10);
            Dimension prefSize1 = new Dimension(20, 10);
            Dimension maxSize1 = new Dimension(30, 15);
            c.gridy = 1;
            panelTasks.add(new Box.Filler(minSize1,prefSize1,maxSize1), c);
            //список возможных заданий
            for (int i = 0; i < names.size(); ++i) {
                String[] diffics = {"1", "2", "3"};

                JButton button = new JButton();
                button.setLayout(new BorderLayout());
                button.add(new JLabel("+"), BorderLayout.CENTER);
                JLabel label = new JLabel(names.get(i).toString());
                Triple triple = new Triple(new JComboBox(diffics),
                        label, button, i);
                triples.add(triple);
                c.gridy = i + 2;
                c.gridx = 0;
                //triple.buttonPlus.setPreferredSize(new Dimension(45, 30));
                panelTasks.add(triple.buttonPlus, c);
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx = 1;
                panelTasks.add(new Box.Filler(minSize1, prefSize1, maxSize1),c);
                c.gridx = 2;
                panelTasks.add(triple.label, c);
                c.gridx = 3;
                panelTasks.add(new Box.Filler(minSize1, prefSize1, maxSize1),c);
                c.fill = GridBagConstraints.CENTER;
                c.gridx = 4;
                panelTasks.add(triple.comboBox, c);
            }

            //панель для вариантов
            JPanel variants = new JPanel();
            variants.setLayout(new BoxLayout(variants, BoxLayout.X_AXIS));
            variants.setAlignmentY(Component.BOTTOM_ALIGNMENT);
            variants.setAlignmentX(Component.CENTER_ALIGNMENT);
            variants.setOpaque(false);

            JLabel numberLabel = new JLabel("кол-во вариантов");
            numberLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            numberLabel.setSize(200,40);

            //pазделитель
            Dimension minSize = new Dimension(20, 20);
            Dimension prefSize = new Dimension(35, 25);
            Dimension maxSize = new Dimension(50, 30);

            numberArea = new TextField();
            numberArea.setMaximumSize(new Dimension(50, numberLabel.getHeight() + 10));

            variants.add(numberLabel);
            variants.add(new Box.Filler(minSize, prefSize, maxSize));
            variants.add(numberArea);

            variants.setMinimumSize(new Dimension(numberLabel.getWidth() + 20 + numberArea.getWidth(), 25));
            variants.setMaximumSize(new Dimension(numberLabel.getWidth() + 20 + numberArea.getWidth(), 25));
            //кнопка создания контрольной
            JButton createButton = new JButton("Создать контрольную");
            createButton.addActionListener(new CreateButtonListener());
            createButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            createButton.setMinimumSize(new Dimension(variants.getWidth(), 50));

            minSize = new Dimension(40, 10);
            prefSize = new Dimension(40, 18);
            maxSize = new Dimension(40, 20);
            Box.Filler verticalEdge = new Box.Filler(minSize, prefSize, maxSize);

            TestText = new JTextArea();
            rightPanel.add(panelTasks);
            rightPanel.add(variants);
            rightPanel.add(verticalEdge);
            rightPanel.add(createButton);
            //rightPanel.add(TestText);

            JLayeredPane layeredPane = new JLayeredPane();
            layeredPane.setPreferredSize(new Dimension(360, 460));
            layeredPane.add(backgroundPanel, new Integer(0));
            layeredPane.add(rightPanel, new Integer(1));

            backgroundPanel.setLocation(0,0);
            backgroundPanel.setBounds(0, 0, 360, 460);
            rightPanel.setLocation(0,0);
            rightPanel.setBounds(0,0,360, 440);
            rightPanel.setOpaque(false);

            frameConstructor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frameConstructor.setLocation(frameTasks.getWidth()+ 10 , 0);
            frameConstructor.setTitle("Генератор контрольных");
            frameConstructor.getContentPane().setLayout(new BorderLayout());
            frameConstructor.getContentPane().add(BorderLayout.CENTER, layeredPane);
            frameConstructor.setSize(360, frameTasks.getHeight());
            frameConstructor.setResizable(false);
            frameConstructor.setVisible(true);
        }

        //класс для элементов правой панели
        class Triple {
            JComboBox comboBox;
            JButton buttonPlus;
            JLabel label;
            int ind;

            Triple(JComboBox combo, JLabel lb, JButton button, int i) {
                comboBox = combo;
                buttonPlus = button;
                label = lb;
                ind = i;

                ButtonAddItem addItem = new ButtonAddItem(this);
                buttonPlus.addActionListener(addItem);
            }
        }

        //кнопка "-"
        class deletePanelListener implements ActionListener {
            JPanel _panel;
            deletePanelListener(JPanel panel){
                _panel = panel;
            }

            public void actionPerformed(ActionEvent event){
                panelMap.remove(_panel);
                leftPanel.remove(_panel);
                listPanel.remove(_panel);
                frameTasks.repaint();
                frameTasks.validate();
            }
        }

        //проверка на возможность преобразовать строку
        boolean tryParseInt(String value) {
            try {
                Integer.parseInt(value);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        public class Example{
            int _typeExample;
            int _difficult;
            Example(int typeExample, int difficult){
                _typeExample = typeExample;
                _difficult = difficult;
            }

            String createExample(String filename){
                try{
                    TreeGenAlgs gen = new TreeGenAlgs();
                    switch (_typeExample){
                        case 0:
                            return gen.print(gen.simple_ex(_difficult));
                        case 1:
                            return gen.print(gen.simple_ex(_difficult));
                        case 2:
                            return gen.print(gen.inequality(_difficult));
                        case 3:
                            return gen.print(gen.equation(_difficult));
                        case 4:
                            return gen.print(gen.simple_ex(_difficult));
                        case 5:
                            return gen.print(gen.simple_ex(_difficult));
                        case 6:
                            return gen.print(gen.simple_ex(_difficult));
                        case 7:
                            return gen.print(gen.simple_ex(_difficult));
                        default:
                            return "8";
                    }
                }
                catch (Exception ex){
                    TestText.setText(ex.getMessage());
                }
                return "8";
            }

        }

        //код для создания контрольной
        class CreateButtonListener implements ActionListener{
            String filename;// = "D:\\java\\fileSchool.txt";
            public void actionPerformed(ActionEvent event){
                //кол-во вариантов
                int numberV = 0;
                try {
                    if (tryParseInt(numberArea.getText()))
                        numberV = Integer.parseInt(numberArea.getText());
                    else {
                        JOptionPane.showMessageDialog(frameConstructor, "количество вариантов должно быть числом");
                        return;
                    }
                    if (numberV <= 0) {
                        JOptionPane.showMessageDialog(frameConstructor, "количество вариантов должно быть больше 0");
                        return;
                    }
                    if (numberV > 500){
                        JOptionPane.showMessageDialog(frameConstructor, "количество вариантов должно быть меньше 500");
                        return;
                    }
                }
                catch (Exception ex){
                    TestText.setText(ex.getMessage());
                }
                //выбираем файл и печатаем в него примеры
                try {
                    filename = getSavePath();
                    if (filename == null)
                        return;
                    TestText.setText(filename);
                    FileWriter writer = new FileWriter(filename, false);
                    writer.close();
                    for (int j =0; j < numberV; ++j) {
                        FileWriter writeVariants = new FileWriter(filename, true);
                        writeVariants.write("Вариант " + (j + 1) + "\n");
                        for (int i = 0; i < listPanel.size(); ++i) {
                            Example example = panelMap.get(listPanel.get(i));
                            writeVariants.write((i + 1) + ". " + example.createExample(filename) + "\n");
                        }
                        writeVariants.write("\n");
                        writeVariants.close();
                    }
                }
                catch (Exception ex){
                    TestText.setText(ex.getMessage());
                }

            }
            //выбирает и возвращает имя файла для сохранения
            String getSavePath() {
                try {
                    final FileDialog fdg = new FileDialog(frameConstructor, "Выберите файл", FileDialog.SAVE);
                    fdg.setDirectory("D:\\");
                    fdg.setFile("control.txt");
                    fdg.setVisible(true);
                    final String dir = fdg.getDirectory();
                    final String file = fdg.getFile();
                    fdg.dispose();
                    if (dir != null && file != null) {
                        return dir + file;
                    } else return null;
                }
                catch (Exception ex){

                }
                return null;
            }

        }
        //класс обработки добавления элемента в список, кнопка "+"
        class ButtonAddItem implements ActionListener {
            Triple triple;
            ButtonAddItem(Triple trip) {
                triple = trip;
            }

            public void actionPerformed(ActionEvent event) {
                if (panelMap.size() >= maxPanels)
                    return;

                //панель для элемента списка заданий
                JPanel pan = new JPanel();
                pan.setLayout(null);
            //тип задания
                JLabel curLabel = new JLabel(triple.label.getText());
                curLabel.setLocation(5, 0);
                curLabel.setSize(labelLength ,compY);
                curLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            //сложность
                JLabel diff = new JLabel(triple.comboBox.getSelectedItem().toString());
                diff.setAlignmentX(Component.RIGHT_ALIGNMENT);
                diff.setLocation(labelLength, 0);
                diff.setSize(space, compY);
            //кнопка для удаления задания из списка
                JButton but = new JButton("-");
                but.addActionListener(new deletePanelListener(pan));
                but.setLocation(labelLength + space, 0);
                but.setSize(butLength, compY);

                pan.add(curLabel);
                pan.add(diff);
                pan.add(but);
                pan.setBackground(new Color(0xF0F8FF));
                pan.setMinimumSize(new Dimension(labelLength + space + butLength, compY + 10));
                pan.setMaximumSize(new Dimension(labelLength + space + butLength, compY + 10));
                pan.setAlignmentX(Component.LEFT_ALIGNMENT);
                Example example = new Example(-1, -1);
                try {
                    example = new Example(triple.ind,
                            Integer.parseInt(triple.comboBox.getSelectedItem().toString()));
                }
                catch(Exception ex){
                    TestText.setText(ex.getStackTrace().toString());
                }
                //добавляем панель на фрейм и в ассоциативный массив
                panelMap.put(pan, example);
                leftPanel.add(pan);
                listPanel.add(pan);
                frameTasks.repaint();
                frameTasks.validate();
            }
        }

        //класс генерации примеров
        class TreeGenAlgs{
            String stringToBox;
            TreeGenAlgs(){
                stringToBox = "";
            }

            class Treenode {
                int data;
                Treenode left;
                Treenode right;
                int leftBrackets;
                int rightBrackets;

                Treenode(int _data, Treenode _left, Treenode _right, int l, int r)
                {
                    data = _data;
                    left = _left;
                    right = _right;
                    leftBrackets = l;
                    rightBrackets = r;
                }

                Treenode(int _data){
                    data = _data;
                    left = null;
                    right = null;
                    leftBrackets = 0;
                    rightBrackets = 0;
                }

            }

            //возвращает делитель
            int getGCD(int digit){
                if (digit == 1)
                    return 1;

                int divider = 1;
                double sqr = Math.sqrt(digit);
                for (int i = 2; i < sqr; ++i){
                    while ((digit % i) == 0) {
                        digit /= i;
                        divider *= i;
                        if ((int)(Math.random() * 2) == 0)
                            return divider;
                    }
                    if (digit == 1)
                        break;
                }
                return divider;
            }

            //доделать
            Treenode simle_ex_creating(int complexity, Treenode t)
            {
                if (t == null)
                {
                    Treenode res = new Treenode ((int) (Math.random() * 5* complexity));
                    return res;
                }
                else
                {
                    int curvel = t.data;
                    int op = (int) ((Math.random() * 4) + 1);
                    if ((t.left == null) && (t.right == null))
                    {
                        //+
                        if (op == 1)
                        {
                            int dx = (int)(Math.random() * 5 * complexity) + 1;
                            t.left = new Treenode (dx);
                            t.right = new Treenode (curvel - dx);
                            if (t.right.data < 0){
                                t.data = -2;
                                t.right.data = Math.abs(t.right.data);
                            }
                            else
                                t.data = -op;
                        }
                        //-
                        if (op == 2)
                        {
                            int dx = (int)(Math.random() * 10) * complexity + 1;
                            t.left = new Treenode (curvel + dx);
                            t.right = new Treenode (dx);
                            t.data = -op;
                        }
                        //*
                        if (op == 3)
                        {
                            int dx1 = getGCD((int)curvel);
                            if (dx1 == 1 || dx1 == curvel)
                                op = 4;
                            else {
                                int dx2 = curvel / dx1;
                                t.left = new Treenode(dx1);
                                t.right = new Treenode(dx2);
                                t.data = -op;
                            }
                        }
                        // /
                        if (op == 4)
                        {
                            int dx = (int) ((Math.random() * 10) * complexity + 1);
                            t.left = new Treenode (curvel * dx);
                            t.right = new Treenode (dx);
                            t.data = -op;
                        }
                    }
                    else
                    {
                        t.left = simle_ex_creating(complexity,t.left);
                        t.right = simle_ex_creating(complexity, t.right);
                    }
                    return t;
                }
            }

            //возвращает текущий пример в виде строки
            String print(Treenode t){
                stringToBox = "";
                if (t != null)
                    _print(t);
                return stringToBox;
            }

            void _print(Treenode  t) {
                if (t.left != null) {
                    t.left.leftBrackets = t.leftBrackets;
                    //t.left.rightBrackets = t.rightBrackets;
                    if (t.data == -4 || (t.data == -3 && (t.left.data == -1 || t.left.data == -2))) {
                        if (t.left.data < 0) {
                            t.left.leftBrackets += 1;
                            t.left.rightBrackets += 1;
                        }
                    }
                    _print(t.left);
                }

                String data = "";
                switch ((int)t.data){
                    case -1:
                        data = "+";
                        break;
                    case -2:
                        data = "-";
                        break;
                    case -3:
                        data = "*";
                        break;
                    case -4:
                        data = "/";
                        break;
                    case -5:
                        data = "=";
                        break;
                    case -6:
                        data = "<";
                        break;
                    case -7:
                        data = ">";
                        break;
                    case -8:
                        data = "<=";
                        break;
                    case -9:
                        data = ">=";
                        break;
                    case -10:
                        data = "x";
                        break;
                    default:
                        data += t.data;
                }

                if (t.data > 0){
                    while (t.leftBrackets > 0){
                        t.leftBrackets --;
                        stringToBox += "(";
                    }
                }
                stringToBox += data + " ";
                if (t.data > 0)
                    while (t.rightBrackets > 0){
                        t.rightBrackets --;
                        stringToBox += ")";
                    }

                if (t.right != null) {
                    //t.right.leftBrackets = t.leftBrackets;
                    t.right.rightBrackets = t.rightBrackets;
                    if (t.data == -4 || (t.data == -3 && (t.right.data == -1 || t.right.data == -2))) {
                        if (t.right.data < 0) {
                            t.right.leftBrackets += 1;
                            t.right.rightBrackets += 1;
                        }
                    }
                    _print(t.right);
                }
            }

            Treenode simple_ex(int complexity)
            {
                Treenode res = null;
                for (int i = 0; i < complexity + 1; ++i)
                {
                    res = simle_ex_creating(complexity, res);
                }
                insertS(res, -5);
                return res;
            }

            /*вставка икса в уравнение с десятичными дробями*/
            void insertx(Treenode  t){
                if ((t.left == null) && (t.right == null))
                {
                    t.left = new Treenode(t.data, null, null, t.leftBrackets, 0);
                    t.leftBrackets = 0;
                    t.data = -10;
                }
                else
                {
                    if (t.data == -4)
                    {
                        insertx(t.left);
                    }
                    else if (Math.random() % 2 == 0)
                    {
                        insertx(t.right);
                    }
                    else
                    {
                        insertx(t.left);
                    }
                }
            }

            /*вставка символа в конец дерева*/
            void insertS(Treenode t, int symb){
                if (t == null)
                    return;
                while (t.right != null)
                    t = t.right;
                t.right = new Treenode(symb);
            }

            /*неравенство*/
            Treenode  inequality(int complexity)
            {
                Treenode res = new Treenode((int)(Math.random() *4) - 9);
                res.left = new Treenode ((int)(Math.random() * 20) + 1);
                res.right = new Treenode (res.left.data+ (int)(Math.random() * 3) - 1);
                for (int i = 0; i < complexity; ++i)
                {
                    res.left = simle_ex_creating(complexity, res.left);
                }
                for (int i = 0; i < complexity; ++i)
                {
                    res.right = simle_ex_creating(complexity, res.right);
                }
                insertx(res.left);
                return res;
            }

            /*уравнение*/
            Treenode equation(int complexity)
            {
                Treenode  res = new Treenode(-5);
                for (int i = 0; i < complexity; ++i)
                {
                    res.left = simle_ex_creating(complexity, res.left);
                }
                for (int i = 0; i < complexity; ++i)
                {
                    res.right = simle_ex_creating(complexity, res.right);
                }
                insertx(res.left);
                if(Math.random() % 2 == 0)
                    insertx(res.right);
                return res;
            }
        }

    }



}





