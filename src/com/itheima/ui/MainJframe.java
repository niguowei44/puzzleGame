package com.itheima.ui;

import java.util.Arrays;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.event.ActionEvent;


public class MainJframe extends JFrame{
    int xPicture = 4, yPicture = 4;
    String picturePath = randomPicture(1, 8);
    String backPicturePath = "image\\background.png";
    String winPicPath = "image\\win.png";
    int[][] winList = new int[][]{{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,0}};
    int[][] pictureOrder;

    private int step = 0;
    
    
    public MainJframe() {
        
        init();

        menuInit();
        // int xPicture = 4, yPicture = 4;
        

        //设置小图片
        pictureOrder = pictureOrder(); // 随机的图片加载顺序
        initPicture(xPicture, yPicture, picturePath, pictureOrder);
        //按钮  里面实现了上下左右的移动 37←  38↑   39→  40↓
        initButton();


        

        // 最后也要让菜单可视化
        // 可见设置必须放在最后加载 不然会导致当在其后面的内容看不到
        this.setVisible(true);
        



    }
    // 主页面 初始化
    public void init() {
        this.setSize(603, 680);
        this.setTitle("拼图单机版V1.0");

        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null); // 设置居中
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // 程序关闭方法  WindowConstants.EXIT_ON_CLOSE = 3 表示关闭窗口就关闭程序
        //取消默认居中排列方式 不取消 this.getContentPane().add(jlabe)中添加的内容不会按照xy轴坐标排列
        this.setLayout(null);
    }

    
    public void menuInit() {
        // 初始化上方的菜单
        JMenuBar jmenuBar = new JMenuBar();
        // 为菜单添加选项卡  功能 关于我们
        JMenu functionJmenu = new JMenu("功能");
        JMenuItem changePicture = new JMenuItem("随机图片");
        JMenuItem restar = new JMenuItem("重新游戏");
        JMenuItem reLogIn = new JMenuItem("重新登陆");
        JMenuItem closeGame = new JMenuItem("关闭游戏");


        JMenu aboutUsJmenu = new JMenu("关于我们");
        JMenuItem officialAccount = new JMenuItem("公众号");

        //JMenu 中添加 JMenuItem  更换图片  重新游戏  重新登陆  关闭游戏
        functionJmenu.add(changePicture);
        functionJmenu.add(restar);
        functionJmenu.add(reLogIn);
        functionJmenu.add(closeGame);

        aboutUsJmenu.add(officialAccount); //关于我们  --> 公众号

        // 将二级菜单添加到菜单中
        //JMenuBar 中添加 JMenu  功能 关于我们
        jmenuBar.add(functionJmenu);
        jmenuBar.add(aboutUsJmenu);

        //在主页面中设置jmenuBar，不设置不能显示
        this.setJMenuBar(jmenuBar);
        
        //重新开始事件监听 addActionListener：鼠标左击 空格
        restar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                step = 0;//重新开始 步数清零
                pictureOrder = pictureOrder(); // 重新生成随机顺序
                initPicture(xPicture, yPicture, picturePath, pictureOrder);
            }
            
        });

        reLogIn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                close();
                new LogInJframe();
            }
            
        });

        closeGame.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // close();
                System.exit(0); 
            }
            
        });

        changePicture.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                step = 0;
                picturePath = randomPicture(1, 8);
                pictureOrder = pictureOrder(); // 重新生成随机顺序
                initPicture(xPicture, yPicture, picturePath, pictureOrder);

            }
            
        });

        officialAccount.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                showPic("image\\niguowei44.png");
            }
            
        });

    }

    private void showPic(String picPath) {
        JLabel aboutMe = new JLabel(new ImageIcon(picPath));
        aboutMe.setBounds(0, 0, 206, 67);
        JDialog aboutMeDialog = new JDialog(getOwner(), "About Me");
        aboutMeDialog.setBounds(0, 0, 226, 75);
        aboutMeDialog.setModal(true); //不关闭 不能操作
        
        aboutMeDialog.setAlwaysOnTop(true);
        aboutMeDialog.setLocationRelativeTo(null);

        aboutMeDialog.getContentPane().add(aboutMe);
        aboutMeDialog.setVisible(true);
    }

    public void close() {
        this.dispose(); // dispose()是隐藏窗口 == setVisible(false);
    }

    private String randomPicture(int star, int end) {
        // 左右都包括
        Random r = new Random();
        String[] pictureType = new String[]{"animal", "girl", "sport"};
        String type = pictureType[r.nextInt(2)];
        int num = r.nextInt(star, end + 1);
        return "image\\" +  type + "\\" + type + num;

    }


    // 该方法只能主界面使用
    private void initPicture(int xNumber, int yNumber, String picturePath, int[][] order) {
        this.getContentPane().removeAll(); //每次刷新图片时，先清空原来的
        int idx = 0;
        // 计步器
        JLabel stepJLabel = new JLabel("步数：" + step);
        stepJLabel.setBounds(50, 20, 100, 50);
        this.getContentPane().add(stepJLabel);

        // 先加载的图片在上层 后加载的图片在下层 因此要先加载胜利图标再加载图片块，然后再加载背景
        if (isWin(order, winList)) {
            JLabel winIcon = new JLabel(new ImageIcon(winPicPath));
            winIcon.setBounds(301, 340, 197, 73);
            this.getContentPane().add(winIcon);
        }


        // // 1.创建ImageIcon对象
        // ImageIcon imageicon = new ImageIcon("C:\\Users\\administered\\Desktop\\game\\image\\animal\\animal1\\1.jpg");
        // //2.容器管理对象
        // JLabel jlabe = new JLabel(imageicon);
        // // 图片位置
        // jlabe.setBounds(0, 0, 105, 105);
        // //获取到界面上的隐藏容器，将容器管理对象放到隐藏容器中
        // this.getContentPane().add(jlabe);
        // 加载15张图片
        for (int i = 0; i < yNumber; i ++ ) {
            for (int j = 0; j < xNumber; j ++ ) {
                //picturePath 为碎片图片所在的目录 (不包含图片名称)
                String fullPath = picturePath + "\\" + order[idx / 4][idx % 4] + ".jpg";
                // System.out.println(fullPath);
                ImageIcon imageicon = new ImageIcon(fullPath);
                JLabel jlabe = new JLabel(imageicon);
                jlabe.setBounds(105 * j + 83, 105 * i + 134, 105, 105);  //参数 x, y, width, height
                jlabe.setBorder(new BevelBorder(BevelBorder.LOWERED));  //设置边框

                this.getContentPane().add(jlabe);
                idx ++;
            }
        }
        // System.out.println("当前工作目录: " + System.getProperty("user.dir"));
        //加载背景图片
        JLabel background = new JLabel(new ImageIcon(backPicturePath));
        background.setBounds(40, 40,508, 560);
        this.getContentPane().add(background);


        this.getContentPane().revalidate(); // 重新布局
        this.getContentPane().repaint();  // 重绘



    }

    private void viewFullPicture(String picturePath) {
        this.getContentPane().removeAll(); //每次刷新图片时，先清空原来的
        ImageIcon imageicon = new ImageIcon(picturePath + "\\All.jpg");
        JLabel jlabe = new JLabel(imageicon);
        jlabe.setBounds(0 + 83, 0 + 134, 420, 420);  //参数 x, y, width, height
        jlabe.setBorder(new BevelBorder(BevelBorder.LOWERED));  //设置边框

        this.getContentPane().add(jlabe);

        JLabel background = new JLabel(new ImageIcon(backPicturePath));
        background.setBounds(40, 40,508, 560);
        this.getContentPane().add(background);


        this.getContentPane().revalidate(); // 重新布局
        this.getContentPane().repaint();  // 重绘
    }

    public int[][] pictureOrder() {
        int[][] answer = new int[][]{{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,0}};
        Random r = new Random();
        int idx = answer[0].length;
        int all = answer.length * idx;
        for (int i = 0; i < all; i ++) {
            int rd = r.nextInt(0, all);
            int x = rd / idx, y = rd % idx; //随机位置
            int xx = x / idx, yy = i % idx; // 当前循环到的位置
            int tempVal = answer[xx][yy];
            answer[xx][yy] = answer[x][y];
            answer[x][y] = tempVal;
        }
        return answer;
    }

    public void initButton() {
        
        // System.out.println(Arrays.toString(zeroIndex));
        // keyboard
        this.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("keyPressed");
                int keyCode = e.getKeyCode();
                if (isWin(pictureOrder, winList)) {
                    return; //赢了 就跳出方法 不能再接收键盘输入了
                    
                }
                switch (keyCode) {
                    case 65:{
                        // x y 都是一张图片
                        viewFullPicture(picturePath);
                        break;
                    }
                        
                    default:
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (isWin(pictureOrder, winList)) {
                    return; //赢了 就跳出方法 不能再接收键盘输入了
                    
                }
                
                int[] zeroIndex = foundZeroArea(pictureOrder);  //按压前要找到空白位置，否则会出现两个空白位置
                switch (keyCode) {
                    case 37:{
                        System.out.println("left");
                        
                        //左移是将空白区域右边的图片向左移动
                        if (zeroIndex[1] <= pictureOrder[zeroIndex[0]].length - 2 ) {
                            step ++;
                            pictureOrder[zeroIndex[0]][zeroIndex[1]] = pictureOrder[zeroIndex[0]][zeroIndex[1] + 1];
                            pictureOrder[zeroIndex[0]][zeroIndex[1] + 1] = 0;
                            initPicture(xPicture, yPicture, picturePath, pictureOrder);
                            zeroIndex[1] = zeroIndex[1] + 1;
                            
                         
                        }
                        break;
                        
                    }
                    case 38:{
                        System.out.println("up");
                        //上移是将空白区域下边的图片向上移动
                        if (zeroIndex[0] <= pictureOrder.length - 2 ) {
                            step ++;
                            pictureOrder[zeroIndex[0]][zeroIndex[1]] = pictureOrder[zeroIndex[0] + 1][zeroIndex[1]];
                            pictureOrder[zeroIndex[0] + 1][zeroIndex[1]] = 0;
                            initPicture(xPicture, yPicture, picturePath, pictureOrder);
                            zeroIndex[0] = zeroIndex[0] + 1;
                            
                        }
                        break;
                    }
                    case 39:{
                        System.out.println("right");
                        //右移是将空白区域左边的图片向右移动
                        if (zeroIndex[1] > 0 ) {
                            step ++;
                            pictureOrder[zeroIndex[0]][zeroIndex[1]] = pictureOrder[zeroIndex[0]][zeroIndex[1] - 1];
                            pictureOrder[zeroIndex[0]][zeroIndex[1] - 1] = 0;
                            initPicture(xPicture, yPicture, picturePath, pictureOrder);
                            zeroIndex[1] = zeroIndex[1] - 1;
                           
                        }
                        break;
                    }
                    case 40:{
                        System.out.println("down");
                        //下移是将空白区域上边的图片向下移动
                        if (zeroIndex[0] > 0 ) {
                            step ++;
                            pictureOrder[zeroIndex[0]][zeroIndex[1]] = pictureOrder[zeroIndex[0] - 1][zeroIndex[1]];
                            pictureOrder[zeroIndex[0] - 1][zeroIndex[1]] = 0;
                            initPicture(xPicture, yPicture, picturePath, pictureOrder);
                            zeroIndex[0] = zeroIndex[0] - 1;
                            
                        }
                        break;
                    }
                    
                    case 65:{
                        System.out.println("viewFullPicture");
                        //松开A按键 恢复图片
                        initPicture(xPicture, yPicture, picturePath, pictureOrder);
                        break;
                    }

                    case 87:{
                        System.out.println("Win");
                        //一键胜利 W键
                        // 局部变量不能被修改 只能放在成员位置后才能修改
                        pictureOrder = new int[][]{{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,0}};
                        initPicture(xPicture, yPicture, picturePath, pictureOrder);
                        zeroIndex[0] = pictureOrder.length - 1;
                        zeroIndex[1] = pictureOrder[zeroIndex[0]].length - 1;
                        break;
                    }

                    default:
                        break;
                }
            }
            
        });
        
        // JButton jbt1 = new JButton("动作监听");
        // JButton jbt2 = new JButton("鼠标监听");
        // // JButton jbt3 = new JButton("键盘监听"); // 键盘最好绑定到当前窗口中


        // jbt1.setBounds(0, 0, 100, 50);
        // jbt2.setBounds(100, 0, 100, 50);
        // // jbt3.setBounds(200,0,100,50);
        
        // // jbt1 action
        // jbt1.addActionListener(new ActionListener() {

        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         System.out.println("jbt1");
        //     }
            
        // });


        // // jbt2 mouse
        // jbt2.addMouseListener(new MouseListener() {

        //     @Override
        //     public void mouseClicked(MouseEvent e) {
        //         // 点击
        //         System.out.println("jbt2 mouseClicked");
        //     }

        //     @Override
        //     public void mousePressed(MouseEvent e) {
        //         // 按下不松
        //         System.out.println("jbt2 mousePressed");
        //     }

        //     @Override
        //     public void mouseReleased(MouseEvent e) {
        //         // 松开
        //         System.out.println("jbt2 mouseReleased");
        //     }

        //     @Override
        //     public void mouseEntered(MouseEvent e) {
        //         // 进入
        //         System.out.println("jbt2 mouseEntered");
        //     }

        //     @Override
        //     public void mouseExited(MouseEvent e) {
        //         // 移出
        //         System.out.println("jbt2 mouseExited");
        //     }
            
        // });
        


        // this.getContentPane().add(jbt1);
        // this.getContentPane().add(jbt2);
        // // this.getContentPane().add(jbt3);
    }

    private int[] foundZeroArea (int[][] area) {
        int[] res = new int[2]; // y, x  行， 列
        for(int y = 0; y < area.length; y ++ ) {
            for (int x = 0; x < area[y].length; x ++ ) {
                if (area[y][x] == 0) {
                    res[0] = y;
                    res[1] = x;
                    break;
                }
            }
        }
        return res;

    }

    public boolean isWin(int[][] answer, int[][] usrAnswer) {
        return Arrays.deepEquals(answer, usrAnswer);
    }

    public void clickOn() {
        System.out.println("clickon");
    }

    
}
