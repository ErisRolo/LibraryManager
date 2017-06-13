package LMS.AccountManage;

import LMS.Main.MainActivity;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by guohouxiao on 2017/6/9.
 * 注册界面
 */
public class RegisterActivity extends javax.swing.JFrame {

    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton okBtn;
    private javax.swing.JButton returnBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel loginAccountLabel;
    private javax.swing.JLabel loginPasswordLabel;
    private javax.swing.JLabel okPassLabel;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPasswordField loginPasswordField;
    private javax.swing.JPasswordField okPasswordField;
    private javax.swing.JRadioButton manBtn;
    private javax.swing.JRadioButton womanBtn;
    private javax.swing.JTextField loginAccountField;
    private javax.swing.JTextField userNameField;
    private javax.swing.JTextField userClassField;
    private javax.swing.JTextField userDeptField;
    private javax.swing.JTextField userPhoneField;

    private Statement statement;

    public RegisterActivity(Statement statement) {
        this.statement = statement;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        loginAccountLabel = new javax.swing.JLabel();
        loginPasswordLabel = new javax.swing.JLabel();
        okPassLabel = new javax.swing.JLabel();
        loginPasswordField = new javax.swing.JPasswordField();
        okPasswordField = new javax.swing.JPasswordField();
        loginAccountField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        userNameField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        userClassField = new javax.swing.JTextField();
        userDeptField = new javax.swing.JTextField();
        userPhoneField = new javax.swing.JTextField();
        okBtn = new javax.swing.JButton();
        returnBtn = new javax.swing.JButton();
        manBtn = new javax.swing.JRadioButton();
        womanBtn = new javax.swing.JRadioButton();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("宋体", 0, 24)); // NOI18N
        jLabel1.setText("登录信息");

        jLabel2.setFont(new java.awt.Font("宋体", 0, 24)); // NOI18N
        jLabel2.setText("用户信息");

        loginAccountLabel.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        loginAccountLabel.setText("登录账号");

        loginPasswordLabel.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        loginPasswordLabel.setText("登录密码");

        okPassLabel.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        okPassLabel.setText("确认密码");

        loginPasswordField.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N

        okPasswordField.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N

        loginAccountField.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N

        jLabel6.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        jLabel6.setText("用户姓名");

        userNameField.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N

        jLabel7.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        jLabel7.setText("用户性别");

        jLabel8.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        jLabel8.setText("用户班级");

        jLabel9.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        jLabel9.setText("用户院系");

        jLabel10.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        jLabel10.setText("联系电话");

        userClassField.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N

        userDeptField.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N

        userPhoneField.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N

        okBtn.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        okBtn.setText("确认");
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //登录信息获取
                String loginAccount = loginAccountField.getText();//获取登录账号
/*                String loginPassword = String.valueOf(loginPasswordField.getPassword());//获取登录密码
                String okPassword = String.valueOf(okPasswordField.getPassword());//获取确认密码*/
                String loginPassword = loginPasswordField.getText();
                String okPassword = okPasswordField.getText();

                //用户信息获取
                String userName = userNameField.getText();//获取用户姓名
                String userClass = userClassField.getText();//获取用户班级
                String userDept = userDeptField.getText();//获取用户院系
                String userTel = userPhoneField.getText();//获取用户电话
                String userSex = null;//获取用户性别
                if (manBtn.isSelected())
                    userSex = "男";
                else if (womanBtn.isSelected())
                    userSex = "女";

                Boolean isexamed = false;//是否审核初始化为false

                ResultSet resultSet = null;

                try {
                    resultSet = statement.executeQuery("SELECT * FROM user WHERE Uno=" + "'" + loginAccount + "'");
                    if (resultSet.next()) {
                        JOptionPane.showMessageDialog(RegisterActivity.this,"该账号已存在。");
                    } else if (loginAccount.equals("")) {
                        JOptionPane.showMessageDialog(RegisterActivity.this,"登录账号不能为空！");
                    } else if (!loginPassword.equals(okPassword)) {
                        JOptionPane.showMessageDialog(RegisterActivity.this,"登录密码必须与确认密码相同！");
                    } else {
                        statement.execute("INSERT INTO user(Uno,Uname,Usex,Uclass,Udept,Upassword,Utel,Uisexamed) VALUES ("
                                + "'" + loginAccount + "'" + ","
                                + "'" + userName + "'" + ","
                                + "'" + userSex + "'" + ","
                                + "'" + userClass + "'" + ","
                                + "'" + userDept + "'" + ","
                                + "'" + loginPassword + "'" + ","
                                + "'" + userTel + "'"
                                + "," + isexamed + ")");
                        JOptionPane.showMessageDialog(RegisterActivity.this,"注册成功！请等待管理员审核。");
                        new MainActivity(statement).setVisible(true);
                        RegisterActivity.this.setVisible(false);
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

            }
        });

        returnBtn.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        returnBtn.setText("返回");
        returnBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AccountItemActivity(statement).setVisible(true);
                RegisterActivity.this.setVisible(false);
            }
        });

        buttonGroup1.add(manBtn);
        manBtn.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        manBtn.setText("男");

        buttonGroup1.add(womanBtn);
        womanBtn.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        womanBtn.setText("女");

        jLabel11.setText("（请输入你的学号作为账号）");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(128, 128, 128)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addGap(170, 170, 170))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(loginPasswordLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(okPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(okPassLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(loginPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(90, 90, 90)
                                                                .addComponent(okBtn))))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(loginAccountLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(loginAccountField))))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(84, 84, 84)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addComponent(jLabel10)
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                        .addComponent(userPhoneField))
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addComponent(jLabel9)
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                        .addComponent(userDeptField))
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addComponent(jLabel8)
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                        .addComponent(userClassField))
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addComponent(jLabel6)
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                        .addComponent(userNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jLabel7)
                                                                .addGap(49, 49, 49)
                                                                .addComponent(manBtn)
                                                                .addGap(39, 39, 39)
                                                                .addComponent(womanBtn)))
                                                .addContainerGap(75, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(returnBtn)
                                                .addGap(251, 251, 251))))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(63, 63, 63)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel6)
                                        .addComponent(userNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(loginAccountLabel)
                                        .addComponent(loginAccountField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel11)
                                .addGap(25, 25, 25)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel7)
                                        .addComponent(loginPasswordLabel)
                                        .addComponent(okPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(manBtn)
                                        .addComponent(womanBtn))
                                .addGap(50, 50, 50)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel8)
                                        .addComponent(okPassLabel)
                                        .addComponent(loginPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(userClassField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(48, 48, 48)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel9)
                                        .addComponent(userDeptField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(41, 41, 41)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel10)
                                        .addComponent(userPhoneField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(okBtn)
                                        .addComponent(returnBtn))
                                .addGap(59, 59, 59))
        );

        pack();
    }


}
