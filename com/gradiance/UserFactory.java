package com.gradiance;

class UserFactory {

    public static User createUser(String id, String name,int role)
    {
        if(role == 1)
            return new Prof(id,name,role);
        return new Student(id,name,role);
    }
}
