package com.qth.model;

import com.qth.model.common.DataTableReqWrapper;

public class Street extends DataTableReqWrapper{
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column street.id
     *
     * @mbg.generated Fri Apr 13 16:10:18 CST 2018
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column street.name
     *
     * @mbg.generated Fri Apr 13 16:10:18 CST 2018
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column street.region
     *
     * @mbg.generated Fri Apr 13 16:10:18 CST 2018
     */
    private Integer region;

    private String regionName;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column street.id
     *
     * @return the value of street.id
     *
     * @mbg.generated Fri Apr 13 16:10:18 CST 2018
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column street.id
     *
     * @param id the value for street.id
     *
     * @mbg.generated Fri Apr 13 16:10:18 CST 2018
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column street.name
     *
     * @return the value of street.name
     *
     * @mbg.generated Fri Apr 13 16:10:18 CST 2018
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column street.name
     *
     * @param name the value for street.name
     *
     * @mbg.generated Fri Apr 13 16:10:18 CST 2018
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column street.region
     *
     * @return the value of street.region
     *
     * @mbg.generated Fri Apr 13 16:10:18 CST 2018
     */
    public Integer getRegion() {
        return region;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column street.region
     *
     * @param region the value for street.region
     *
     * @mbg.generated Fri Apr 13 16:10:18 CST 2018
     */
    public void setRegion(Integer region) {
        this.region = region;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
}