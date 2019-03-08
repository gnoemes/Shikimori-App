package com.gnoemes.shikimoriapp.entity.roles.presentation.person;

import com.gnoemes.shikimoriapp.entity.common.domain.Image;

public class PersonHeadItem extends BasePersonItem {

    private long id;
    private Image image;
    private String name;
    private String russianName;
    private String japaneeseName;
    private String jobTitle;
    private String birthDay;
    private String roles;

    public PersonHeadItem(long id, Image image, String name, String russianName, String japaneeseName, String jobTitle, String birthDay, String roles) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.russianName = russianName;
        this.japaneeseName = japaneeseName;
        this.jobTitle = jobTitle;
        this.birthDay = birthDay;
        this.roles = roles;
    }

    public long getId() {
        return id;
    }

    public Image getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getRussianName() {
        return russianName;
    }

    public String getJapaneeseName() {
        return japaneeseName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public String getRoles() {
        return roles;
    }
}
