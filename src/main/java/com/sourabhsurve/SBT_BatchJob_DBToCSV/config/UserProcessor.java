package com.sourabhsurve.SBT_BatchJob_DBToCSV.config;

import com.sourabhsurve.SBT_BatchJob_DBToCSV.entity.User;
import org.springframework.batch.item.ItemProcessor;


public class UserProcessor implements ItemProcessor<User,User> {
    @Override
    public User process(User user) throws Exception {
//        user.setName(user.getName().toUpperCase());
//        user.setDept(user.getDept().toUpperCase());
//       //user.setSalary(user.getSalary().multiply(new BigDecimal(2)));
//       // user.setSalary(user.getSalary());
        return user;
    }
}
