package ${packageName}.common;


import java.io.Serializable;
import java.util.List;

/**
 * PageList分页处理类
 * PageList.java
 * @author ${authorName}
 * @date ${.now?string('yyyy/MM/dd')}
 */
public class PageList<T extends Serializable> {

    /**
     * 总记录数
     */
    private int totalCount;

    /**
     * 总页数
     */
    private int totalPageCount;

    /**
     * 开始查询的页数
     */
    private int startPageNo;

    /**
     * 查询的偏移量【每页查询的最大条数】
     */
    private int pageSize;

    /**
     * 对象集合
     */
    private List<T> list;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public int getStartPageNo() {
        return startPageNo;
    }

    public void setStartPageNo(int startPageNo) {
        this.startPageNo = startPageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

}