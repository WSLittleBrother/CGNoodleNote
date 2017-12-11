package com.example.a99351.cgnoodlenote.localdata.busdb;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by 99351 on 2017/12/6.
 */
@DatabaseTable(tableName = "Product")
public class Product {
    @DatabaseField(generatedId = true, unique = true)
    private int id;
    @DatabaseField(width = 20)
    private String productname;
    @DatabaseField
    private String price;
    @DatabaseField
    private String weight;
    @DatabaseField
    private String sumprice;
    @DatabaseField
    private String unit;
    @DatabaseField
    private String createdate;
    @DatabaseField
    private String remake;//备注
    @DatabaseField
    private String imgurl;//产品图像地址

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getRemake() {
        return remake;
    }

    public void setRemake(String remake) {
        this.remake = remake;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getSumprice() {
        return sumprice;
    }

    public void setSumprice(String sumprice) {
        this.sumprice = sumprice;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }
}
