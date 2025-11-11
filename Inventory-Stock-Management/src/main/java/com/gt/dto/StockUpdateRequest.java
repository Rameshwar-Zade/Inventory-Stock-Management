package com.gt.dto;

public class StockUpdateRequest {
    private int quantityChange; // +ve for add, -ve for reduce

    public int getQuantityChange() {
        return quantityChange;
    }

    public void setQuantityChange(int quantityChange) {
        this.quantityChange = quantityChange;
    }
}
