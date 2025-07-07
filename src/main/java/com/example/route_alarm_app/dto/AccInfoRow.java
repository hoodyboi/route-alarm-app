package com.example.route_alarm_app.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class AccInfoRow {

    @JacksonXmlProperty(localName = "acc_id")
    private String accId;

    @JacksonXmlProperty(localName = "occr_date")
    private String occrDate;

    @JacksonXmlProperty(localName = "occr_time")
    private String occrTime;

    @JacksonXmlProperty(localName = "acc_type")
    private String accType;

    @JacksonXmlProperty(localName = "grs80tm_x")
    private double grs80tmX;

    @JacksonXmlProperty(localName = "grs80tm_y")
    private double grs80tmY;

    @JacksonXmlProperty(localName = "acc_info")
    private String accInfo;
}
