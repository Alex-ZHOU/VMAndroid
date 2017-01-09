/*
 * Copyright 2016 Alex_ZHOU
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alex.vmandroid.entities;

public class Login {

    /**
     * 用户信息
     */
    private User User = new User();

    /**
     * 返回的数据
     */
    private String Return;

    public User getUser() {
        return User;
    }

    public void setUser(User user) {
        User = user;
    }

    public String getReturn() {
        return Return;
    }

    public void setReturn(String return1) {
        Return = return1;
    }

    public class User {

        /**
         * 账户名
         */
        private String UserName;

        /**
         * 密码
         */
        private String Password;

        /**
         * 用户id号
         */
        private int userId;

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String userName) {
            UserName = userName;
        }

        public String getPassword() {
            return Password;
        }

        public void setPassword(String password) {
            Password = password;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

    }
}
