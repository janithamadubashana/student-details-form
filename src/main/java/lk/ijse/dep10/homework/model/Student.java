package lk.ijse.dep10.homework.model;

import lk.ijse.dep10.homework.util.Gender;

import java.io.Serializable;
import java.util.ArrayList;

public class Student implements Serializable {
    private String id;
    private String name;
    private Gender gender;
    private ArrayList<String> contacts;
    private String degree;
    private ArrayList<String> selectedModules;
    private boolean partTime;


    public Student(String id, String name, Gender gender, ArrayList<String> contacts, String degree, ArrayList<String> selectedModules, boolean partTime) {
        this.setId(id);
        this.setName(name);
        this.setGender(gender);
        this.setContacts(contacts);
        this.setDegree(degree);
        this.setSelectedModules(selectedModules);
        this.setPartTime(partTime);
    }

    public Student() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public ArrayList<String> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<String> contacts) {
        this.contacts = contacts;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public ArrayList<String> getSelectedModules() {
        return selectedModules;
    }

    public void setSelectedModules(ArrayList<String> selectedModules) {
        this.selectedModules = selectedModules;
    }

    public boolean isPartTime() {
        return partTime;
    }

    public void setPartTime(boolean partTime) {
        this.partTime = partTime;
    }
}
