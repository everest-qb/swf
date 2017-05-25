/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swf.item;
import java.io.Serializable;
/**
 *
 * @author andy.dh.chen
 */
public class ItemContent implements Serializable{
    private int type;
    private String data;
    private int compId;
    private long lineId;
    private String compName; 
    private boolean isLine;

    public int getType() {
        return type;
    }

    public String getData() {
        return data;
    }

    public int getCompId() {
        return compId;
    }

    public long getLineId() {
        return lineId;
    }

    public String getCompName() {
        return compName;
    }

    public boolean isIsLine() {
        return isLine;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setCompId(int compId) {
        this.compId = compId;
    }

    public void setLineId(long lineId) {
        this.lineId = lineId;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public void setIsLine(boolean isLine) {
        this.isLine = isLine;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ItemContent other = (ItemContent) obj;
        if (this.compId != other.compId) {
            return false;
        }
        if (this.lineId != other.lineId) {
            return false;
        }
        return true;
    }
    
    
}
