package com.project.sightseeing.Commentary;

public enum Rate {one("1"), two("2"), three("3"), four("4"), five("5");
	
	private final String displayValue;
    
    private Rate(String displayValue) {
        this.displayValue = displayValue;
    }
     
    public String getDisplayValue() {
        return displayValue;
    }
}
