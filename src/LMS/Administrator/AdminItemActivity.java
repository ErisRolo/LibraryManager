package LMS.Administrator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Statement;

/**
 * Created by guohouxiao on 2017/6/9.
 * 管理员选项界面
 */
public class AdminItemActivity extends javax.swing.JFrame {

    private javax.swing.JButton userBtn;
    private javax.swing.JButton bookBtn;
    private javax.swing.JLabel jLabel1;

    private Statement statement;

    public AdminItemActivity(Statement statement) {
        this.statement = statement;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        userBtn = new javax.swing.JButton();
        bookBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("宋体", 0, 36)); // NOI18N
        jLabel1.setText("         管理员系统选项");

        userBtn.setFont(new java.awt.Font("宋体", 0, 24)); // NOI18N
        userBtn.setText("用户信息管理");
        userBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminUserActivity(statement).setVisible(true);
                AdminItemActivity.this.setVisible(false);
            }
        });

        bookBtn.setFont(new java.awt.Font("宋体", 0, 24)); // NOI18N
        bookBtn.setText("图书信息管理");
        bookBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminBookActivity(statement).setVisible(true);
                AdminItemActivity.this.setVisible(false);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(202, 202, 202)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(bookBtn)
                                        .addComponent(userBtn))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(67, 67, 67)
                                .addComponent(userBtn)
                                .addGap(65, 65, 65)
                                .addComponent(bookBtn)
                                .addGap(0, 94, Short.MAX_VALUE))
        );

        pack();
    }

}
