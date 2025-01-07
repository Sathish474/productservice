package com.sathish.productservice.services.sortingService;

import com.sathish.productservice.dto.search.SortingCriteria;

public class SorterFactory {
    public static Sorter getSorter(SortingCriteria sortingCriteria) {
       return switch (sortingCriteria) {
           case PRICE_HIGH_TO_LOW -> new PriceHighToLowSorter();
           case PRICE_LOW_TO_HIGH -> new PriceLowToHighSorter();
           case RELEVANCE -> null;
           case POPULARITY -> null;
           case RATING_HIGH_TO_LOW -> null;
           case SORTING_CRITERIA -> null;
           case RATING_LOW_TO_HIGH -> null;
       };
    }
}
