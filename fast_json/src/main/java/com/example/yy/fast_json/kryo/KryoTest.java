package com.example.yy.fast_json.kryo;

public class KryoTest {
    public static void main(String[] args){
        Father father1 = new Father("Mike",34,new Child("Mikeson",15,78));
        byte[] bytes1 = KryoUtil.writeToByteArray(father1);


        Father father1New = KryoUtil.readFromByteArray(bytes1);
        System.out.println(father1 + "/" + father1New);
        System.out.println(father1.hashCode() + "/" + father1New.hashCode());

        Child c1 = new Child("Mikeson",15,89);
        Father f1 = new Father("Mike",35,KryoUtil.cloneObj(c1));
        System.out.println(f1);
        c1.setAge(0);
        System.out.println(f1);

        Father ff = KryoUtil.cloneObj(father1);
        System.out.println(ff);
    }
}
