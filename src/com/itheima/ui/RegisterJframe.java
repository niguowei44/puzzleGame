package com.itheima.ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.io.IOException;


import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.itheima.person.Person;

public class RegisterJframe extends JFrame{

    private String userNamePath = "image\\register\\注册用户名.png";
    private String passwordPath = "image\\register\\注册密码.png";
    private String againPasswordPath = "image\\register\\再次输入密码.png";
    private String registerPath = "image\\register\\注册按钮.png";
    private String reSetPath = "image\\register\\重置按钮.png";
    private String backgroundPath = "image\\register\\background.png";

    

    JLabel registerIcon;
    JLabel reSetIcon;

    String usrname;
    String pswd;
    String againpswd;

    // Person p = new Person();
    

    // RegisterJframe继承了JFrame，自然就拥有了JFrame的成员方法与属性 可以直接用public提供的方法设置对应的属性
    public RegisterJframe() {
        initRegisterFrame();
        

        initButtonRegisterAndReSet();

        this.setVisible(true);
        
    }

    private void initRegisterFrame() {
        this.setSize(470,390);
        this.setTitle("拼图注册");
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null); // 设置居中
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE); // 程序关闭方法  WindowConstants.EXIT_ON_CLOSE = 3 表示关闭窗口就关闭程序
        //取消默认居中排列方式 不取消 this.getContentPane().add(jlabe)中添加的内容不会按照xy轴坐标排列
        this.setLayout(null);
        
    }


    public void initContent() {
        //username
        JLabel userNameLabel = new JLabel(new ImageIcon(userNamePath));
        userNameLabel.setBounds(122, 150, 79, 17);

        JTextField userNameField = new JTextField();
        userNameField.setBounds(132 + 79 + 15, 150, 100, 17);
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
        passwordLabel.setBounds(122, 150 + 17 + 50, 64, 16);

        JTextField passwordField = new JTextField();
        passwordField.setBounds(132 + 79 + 15, 150 + 17 + 50, 100, 17);
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


        JLabel againPasswordJLabel = new JLabel(new ImageIcon(againPasswordPath));
        againPasswordJLabel.setBounds(122, 150 + 17 + 50 + 50, 96, 17);
        JTextField againPassWordJTextField = new JTextField();
        againPassWordJTextField.setBounds(132 + 79 + 15, 150 + 17 + 50 + 50, 100, 17);
        againPassWordJTextField.addKeyListener(new KeyListener() {

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
                    againpswd = againPassWordJTextField.getText();
                    System.out.println(againpswd);
                }
            }
            
        });

        JLabel backgroundJLabel = new JLabel(new ImageIcon(backgroundPath));
        backgroundJLabel.setBounds(0, 0, 470, 390);




        this.getContentPane().add(userNameLabel);
        this.getContentPane().add(userNameField);

        this.getContentPane().add(passwordLabel);
        this.getContentPane().add(passwordField);

        this.getContentPane().add(againPasswordJLabel);
        this.getContentPane().add(againPassWordJTextField);

        this.getContentPane().add(registerIcon);
        this.getContentPane().add(reSetIcon);

        this.getContentPane().add(backgroundJLabel);
    }


    private void initButtonRegisterAndReSet() {
        this.getContentPane().removeAll(); //每次刷新图片时，先清空原来的
        

        registerIcon = new JLabel(new ImageIcon(registerPath));
        registerIcon.setBounds(110, 300, 128, 47);
        registerIcon.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //
                registerIcon.setIcon(new ImageIcon("image\\register\\注册按下.png"));
                System.out.println("注册按下");
                // initContent();
                try {
                        if (Person.isContainUser(usrname)) {
                            showMessage("用户名已经存在");
                        }
                    } catch (IOException e1) {
                        
                        showMessage("请输入用户名或密码");
                    }
                

                
                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                registerIcon.setIcon(new ImageIcon(registerPath));
                System.out.println("注册松开");
                if (pswd.equals(againpswd)) {
                    try {

                        Person.saveUser(usrname, pswd);
                        showMessage("注册成功");
                        close();
                        
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    showMessage("两次密码不一致");
                }
                              
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                
               
            }

            @Override
            public void mouseExited(MouseEvent e) {
                
            }
            
        });
        reSetIcon = new JLabel(new ImageIcon(reSetPath));
        reSetIcon.setBounds(110 + 12 + 128, 300, 128, 47);
        reSetIcon.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //
                reSetIcon.setIcon(new ImageIcon("image\\register\\重置按下.png"));
                System.out.println("重置按下");
                
                
                
                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                reSetIcon.setIcon(new ImageIcon(reSetPath));
                System.out.println("重置松开");
                
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
