package me.zjc.swfu.tableBean;

import java.util.List;

/**
 * Created by ChuanZhangjiang on 2016/4/6.
 */
public class CourseTime {
    private Integer whatDay;
    private List<Integer> section;

    public List<Integer> getSection() {
        return section;
    }

    public void setSection(List<Integer> section) {
        this.section = section;
    }

    public Integer getWhatDay() {
        return whatDay;
    }

    public void setWhatDay(Integer whatDay) {
        this.whatDay = whatDay;
    }
}
