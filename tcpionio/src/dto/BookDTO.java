package dto;

import java.util.List;

public class BookDTO {

    private static List<Integer> NoList;
    private static List<String> titleList;
    private static List<String> categoryList;
    private static List<String> langList;
    private static List<String> authorList;

    private static List<String> yearList;

    private static List<String> priceList;

    public static List<Integer> getNoList() {
        return NoList;
    }

    public static void setNoList(List<Integer> noList) {
        NoList = noList;
    }

    public static List<String> getTitleList() {
        return titleList;
    }

    public static void setTitleList(List<String> titleList) {
        BookDTO.titleList = titleList;
    }

    public static List<String> getCategoryList() {
        return categoryList;
    }

    public static void setCategoryList(List<String> categoryList) {
        BookDTO.categoryList = categoryList;
    }

    public static List<String> getLangList() {
        return langList;
    }

    public static void setLangList(List<String> langList) {
        BookDTO.langList = langList;
    }

    public static List<String> getAuthorList() {
        return authorList;
    }

    public static void setAuthorList(List<String> authorList) {
        BookDTO.authorList = authorList;
    }

    public static List<String> getYearList() {
        return yearList;
    }

    public static void setYearList(List<String> yearList) {
        BookDTO.yearList = yearList;
    }

    public static List<String> getPriceList() {
        return priceList;
    }

    public static void setPriceList(List<String> priceList) {
        BookDTO.priceList = priceList;
    }
}
