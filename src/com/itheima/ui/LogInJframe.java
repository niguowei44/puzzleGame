package com.itheima.ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.itheima.person.Person;

public class LogInJframe extends JFrame{

    private String userNamePath = "image\\login\\用户名.png"; //47 17
    private String passwordPath = "image\\login\\密码.png"; //32 16
    private String codePath = "image\\login\\验证码.png"; //56 21
    private String loginPath = "image\\login\\登录按钮.png"; //128 47
    private String registerPath = "image\\login\\注册按钮.png"; //128 47
    private String backgroundPath = "image\\login\\background.png"; // 470 390
    String verify = generateVerifyCode(5);

    String usrname;
    String pswd;


    JLabel loginIcon;
    JLabel registerIcon;

    // 构造方法
    public LogInJframe() {
        initLogInFrame();

        initButtonRegisterAndLogIn();
        






        this.setVisible(true);
    }

    private void initLogInFrame() {
        this.setSize(488,430);
        this.setTitle("拼图登录");
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null); // 设置居中
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // 程序关闭方法  WindowConstants.EXIT_ON_CLOSE = 3 表示关闭窗口就关闭程序
        //取消默认居中排列方式 不取消 this.getContentPane().add(jlabe)中添加的内容不会按照xy轴坐标排列
        this.setLayout(null);
        
    }


    private void initContent() {
        System.out.println("initContent");

        // this.getContentPane().removeAll(); //清空会导致登录按钮 失效



        //username
        JLabel userNameLabel = new JLabel(new ImageIcon(userNamePath));
        userNameLabel.setBounds(132, 150, 47, 17);

        JTextField userNameField = new JTextField();
        userNameField.setBounds(132 + 47 + 15, 150, 100, 17);
        userNameField.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    usrname = userNameField.getText();
                    System.out.println(usrname);

                    
                }
            }
            
        });

        //password
        JLabel passwordLabel = new JLabel(new ImageIcon(passwordPath));
        passwordLabel.setBounds(132, 150 + 17 + 50, 32, 16);

        JTextField passwordField = new JTextField();
        passwordField.setBounds(132 + 47 + 15, 150 + 17 + 50, 100, 17);
        passwordField.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // enter 10
                if (e.getKeyCode() == 10) {
                    pswd = passwordField.getText();
                    System.out.println(pswd);
                }
            }
            
        });
        



        //code
        JLabel codeLabel = new JLabel(new ImageIcon(codePath));
        codeLabel.setBounds(132, 150 + 17 * 2 + 100, 56, 21);

        JTextField codeField = new JTextField();
        codeField.setBounds(132 + 47 + 15, 150 + 17 * 2 + 100, 100, 20);
        codeField.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // enter 10
                if (e.getKeyCode() == 10) {
                    String vercode = codeField.getText();
                    System.out.println(vercode);
                    if (!vercode.equals(verify)) {
                        showMessage("验证码错误");

                    }
                }
            }
            
        });

        
        JLabel verifyJLabel = new JLabel(verify);
        verifyJLabel.setBounds(132 + 47 + 15 + 100 + 10, 150 + 17 * 2 + 100, 100, 18);
        verifyJLabel.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                
            }

            @Override
            public void mousePressed(MouseEvent e) {
                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                verify = generateVerifyCode(5);
                System.out.println("new verify: " + verify);
                initContent();
               
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
                
            }
            
        });


        JLabel backgroundLabel = new JLabel(new ImageIcon(backgroundPath));
        backgroundLabel.setBounds(0, 0, 470, 390);
        
        
       
        

        this.getContentPane().add(userNameLabel);
        this.getContentPane().add(userNameField);

        this.getContentPane().add(passwordLabel);
        this.getContentPane().add(passwordField);

        this.getContentPane().add(codeLabel);
        this.getContentPane().add(codeField);

        this.getContentPane().add(verifyJLabel);

        this.getContentPane().add(loginIcon);
        this.getContentPane().add(registerIcon);

        this.getContentPane().add(backgroundLabel);




    }

    private void initButtonRegisterAndLogIn() {
        this.getContentPane().removeAll(); //每次刷新图片时，先清空原来的
        
        System.out.println("initButtonRegisterAndLogIn");

        loginIcon = new JLabel(new ImageIcon(loginPath));
        loginIcon.setBounds(110, 320, 128, 47);
        loginIcon.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //
                loginIcon.setIcon(new ImageIcon("image\\login\\登录按下.png"));
                System.out.println("登录按下");
                // initContent();

                
                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                loginIcon.setIcon(new ImageIcon("image\\login\\登录按钮.png"));
                System.out.println("登录松开");
                // initContent();
                try {
                    
                    if (Person.validateUser(usrname, pswd)) {
                        close();
                        new MainJframe();
                        
                    } else {
                        showMessage("用户名或密码错误");
                    }
                } catch (IOException e1) {
                    showMessage("请输入用户名或密码");
                }
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                
               
            }

            @Override
            public void mouseExited(MouseEvent e) {
                
            }
            
        });
        registerIcon = new JLabel(new ImageIcon(registerPath));
        registerIcon.setBounds(110 + 12 + 128, 320, 128, 47);
        registerIcon.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //
                registerIcon.setIcon(new ImageIcon("image\\login\\注册按下.png"));
                System.out.println("注册按下");
                
                
                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                registerIcon.setIcon(new ImageIcon("image\\login\\注册按钮.png"));
                System.out.println("注册松开");
                new RegisterJframe();
                
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
               
            }

            @Override
            public void mouseExited(MouseEvent e) {
                
                
            }
            
        });


        initContent();
        

        this.getContentPane().revalidate(); // 重新布局
        this.getContentPane().repaint();  // 重绘

        System.out.println("end of initButtonRegisterAndLogIn");


        
    }

    public String generateVerifyCode(int len){
        String CODE_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        String NUMBER_POOL = "0123456789";
        Random r = new Random();
        int idx = r.nextInt(len);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i ++) {
            int temp = r.nextInt(0, CODE_POOL.length());
            if (i == idx) {
                sb.append(NUMBER_POOL.charAt(r.nextInt(0, NUMBER_POOL.length())));
            } else{
                sb.append(CODE_POOL.charAt(temp));
            }
            

        }
        return sb.toString();
        

    }

    private void showMessage(String mesage) {
        JDialog messageDialog = new JDialog(getOwner(), mesage);

        JLabel messagLabel = new JLabel(mesage);
        
        messageDialog.setBounds(0, 0, 200, 150);
        messageDialog.setModal(true); //不关闭 不能操作
        
        messageDialog.setAlwaysOnTop(true);
        messageDialog.setLocationRelativeTo(null);

        messageDialog.getContentPane().add(messagLabel);
        messageDialog.setVisible(true);
        

    }

    public void close() {
        this.dispose(); // dispose()是隐藏窗口 == setVisible(false);
    }

    
}
 