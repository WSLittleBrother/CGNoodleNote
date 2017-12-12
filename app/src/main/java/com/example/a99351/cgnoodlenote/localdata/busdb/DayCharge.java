package com.example.a99351.cgnoodlenote.localdata.busdb;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by 99351 on 2017/12/12.
 */
@DatabaseTable(tableName = "DayCharge")
public class DayCharge {
    @DatabaseField(generatedId = true, unique = true)
    private int id;
    @DatabaseField
    private int productid;
    @DatabaseField
    private String createdate;
    @DatabaseField
    private String productname;
    @DatabaseField
    private String productweight;
    @DatabaseField
    private String productprice;
    @DatabaseField
    private String productunit;
    @DatabaseField
    private String productsumprice;
    @DatabaseField
    private String productimg;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductweight() {
        return productweight;
    }

    public void setProductweight(String productweight) {
        this.productweight = productweight;
    }

    public String getProductprice() {
        return productprice;
    }

    public void setProductprice(String productprice) {
        this.productprice = productprice;
    }

    public String getProductunit() {
        return productunit;
    }

    public void setProductunit(String productunit) {
        this.productunit = productunit;
    }

    public String getProductsumprice() {
        return productsumprice;
    }

    public void setProductsumprice(String productsumprice) {
        this.productsumprice = productsumprice;
    }

    public String getProductimg() {
        return productimg;
    }

    public void setProductimg(String productimg) {
        this.productimg = productimg;
    }
}
