package com.gradiance;

class UserFactory {

    public static User createUser(String new_role)
    {
        if(new_role.equals("prof"))
        {
            return new Prof();
        }
        return new Student();
    }
}
