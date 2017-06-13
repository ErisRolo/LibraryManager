package LMS.AccountManage;

import LMS.Main.MainActivity;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Statement;

/**
 * Created by guohouxiao on 2017/6/10.
 * 个人中心界面
 */
public class AccountItemActivity extends javax.swing.JFrame {

    private javax.swing.JButton registerBtn;
    private javax.swing.JButton changeBtn;
    private javax.swing.JButton returnBtn;
    private javax.swing.JLabel jLabel1;

    private Statement statement;


    public AccountItemActivity(Statement statement) {
        this.statement = statement;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        registerBtn = new javax.swing.JButton();
        changeBtn = new javax.swing.JButton();
        returnBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("宋体", 0, 36)); // NOI18N
        jLabel1.setText("        用户个人中心选项");

        registerBtn.setFont(new java.awt.Font("宋体", 0, 24)); // NOI18N
        registerBtn.setText("注册账号");
        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegisterActivity(statement).setVisible(true);
                AccountItemActivity.this.setVisible(false);
            }
        });

        changeBtn.setFont(new java.awt.Font("宋体", 0, 24)); // NOI18N
        changeBtn.setText("修改密码");
        changeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ChangeActivity(statement).setVisible(true);
                AccountItemActivity.this.setVisible(false);
            }
        });

        returnBtn.setFont(new java.awt.Font("宋体", 0, 24)); // NOI18N
        returnBtn.setText("返回上层");
        returnBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainActivity(statement).setVisible(true);
                AccountItemActivity.this.setVisible(false);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(217, 217, 217)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(returnBtn)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(changeBtn)
                                                .addComponent(registerBtn)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(60, 60, 60)
                                .addComponent(registerBtn)
                                .addGap(34, 34, 34)
                                .addComponent(changeBtn)
                                .addGap(35, 35, 35)
                                .addComponent(returnBtn)
                                .addGap(0, 60, Short.MAX_VALUE))
        );

        pack();
    }

}

