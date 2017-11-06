package com.dto;

public class NameIdDto implements Comparable<NameIdDto> {
    private int id;
    private String name;

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    @Override
    public int compareTo(NameIdDto obj) {
	int x = String.CASE_INSENSITIVE_ORDER.compare(this.getName(), obj.getName());
	if (x == 0) {
	    x = this.getName().compareTo(obj.getName());
	}
	return x;
    }

    @Override
    public boolean equals(Object obj) {
	// TODO Auto-generated method stub
	if (this == obj) {
	    return true;
	}
	if (this == null || obj == null) {
	    return false;
	}
	if (this.getId() == ((NameIdDto) obj).getId()) {
	    return true;
	} else {
	    return false;
	}
    }

    @Override
    public int hashCode() {
	// TODO Auto-generated method stub
	return id;
    }

    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("NameIdDto [id=");
	builder.append(id);
	builder.append(", name=");
	builder.append(name);
	builder.append("]");
	return builder.toString();
    }

}
