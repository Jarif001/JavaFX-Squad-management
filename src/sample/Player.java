package sample;

import java.io.Serializable;

public class Player implements Serializable {
    String name;
    String country;
    String pos, club;
    int age, jNo;
    double height, salary;
    String transferStatus = "Not Listed";
    Player(String _name, String _coun, String _pos, int _age, int _jNo, double _h, double _sal, String _club){
        name = _name;
        country = _coun;
        pos = _pos;
        age = _age;
        jNo = _jNo;
        height = _h;
        salary = _sal;
        club = _club;
    }
    public String getName(){
        return name;
    }
    public String getCountry(){
        return country;
    }
    public String getClub(){
        return club;
    }
    public String getPos(){
        return pos;
    }
    public int getAge(){
        return age;
    }
    public double getHeight(){
        return height;
    }
    public double getSalary(){
        return salary;
    }
    public String toString(){
        return name + ", Country: " + country + ", Age: " + age + ", Height: " + height + ", Club: " + club + ", Position: " + pos
                + ", Jersey No: " + jNo + ", Salary: " + salary;
    }
    public String infoToFile(){
        return name + "," + country + "," + age + "," + height + "," + club + "," + pos + "," + jNo + "," + salary;
    }

    public void setTransferStatus(String s){
        transferStatus = s;
    }
    public void setClub(String club){
        this.club = club;
    }

    public String getTransferStatus(){
        return transferStatus;
    }
}
