package us.careydevelopment.util.analytics.util;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.ui.Model;

import com.fasterxml.jackson.databind.ObjectMapper;

import us.careydevelopment.util.analytics.constant.Sources;
import us.careydevelopment.util.analytics.model.BaseWebPageVisit;
import us.careydevelopment.util.analytics.model.CategoryView;
import us.careydevelopment.util.analytics.model.TagView;

public class ModelUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    private static final int MAX_SOURCES = 10;
    private static final int SOURCE_THRESHOLD = 25;
    private static final int MAX_TAGS = 10;
    private static final int MAX_CATEGORIES = 5;

    
    public static void setSourceMetricsAttributes(List<? extends BaseWebPageVisit> visits, Model model) throws Exception {
        Map<String, Long> sourceMetrics = visits
                                            .stream()
                                            .map(visit -> SourceUtil.getBrandNameFromUrl(visit.getReferer()))
                                            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                                            .entrySet()
                                            .stream()
                                            .limit(MAX_SOURCES)
                                            .filter(entry -> entry.getValue() > SOURCE_THRESHOLD)
                                            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))                         
                                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                                    (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        
        //thymeleaf doesn't interpret the data set as strings, so 
        //we need to put it in JSON format
        String sourceJson = MAPPER.writeValueAsString(sourceMetrics.keySet());
        model.addAttribute("sources", sourceJson);            
        model.addAttribute("counts", sourceMetrics.values());
    }
    
    
    public static void setSourcesByDateAttributes(List<? extends BaseWebPageVisit> visits, Model model) throws Exception {
        Map<String, Map<String, Long>> sourcesMap = new HashMap<>();
        
        for (int i = 0; i < Sources.SOURCE_BRAND_NAMES.length; i++) {
            String brandName = Sources.SOURCE_BRAND_NAMES[i];
           
            Map<String, Long> map = visits
                                        .stream()
                                        .filter(visit -> brandName.equals(SourceUtil.getBrandNameFromUrl(visit.getReferer())))
                                        .map(visit -> visit.getDateOfVisit())
                                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                                        .entrySet()
                                        .stream()
                                        .sorted(Map.Entry.comparingByKey())                         
                                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                                (oldValue, newValue) -> oldValue, LinkedHashMap::new));
            
            sourcesMap.put(brandName, map);
        }
        
        Set<String> dates = sourcesMap.get(Sources.GOOGLE_BRAND).keySet();
        String datesJson = MAPPER.writeValueAsString(dates);        
        model.addAttribute("dates", datesJson);
        
        model.addAttribute("duckDuckGoReferrals", sourcesMap.get(Sources.DUCKDUCKGO_BRAND).values());
        model.addAttribute("googleReferrals", sourcesMap.get(Sources.GOOGLE_BRAND).values());
        model.addAttribute("careyReferrals", sourcesMap.get(Sources.CAREYDEVELOPMENT_BRAND).values());
        model.addAttribute("bingReferrals", sourcesMap.get(Sources.BING_BRAND).values());
    }
    
    
    public static void setTagViewsAttributes(List<TagView> views, Model model) throws Exception {
        model.addAttribute("views", views);
        
        Map<String, Long> map = views
                                    .stream()
                                    .limit(MAX_TAGS)
                                    .collect(Collectors.toMap(TagView::getTag, TagView::getCount, 
                                            (oldValue, newValue) -> newValue, LinkedHashMap::new));
                    
        //thymeleaf doesn't interpret the data set as strings, so 
        //we need to put it in JSON format
        String tagsJson = MAPPER.writeValueAsString(map.keySet());
        model.addAttribute("tags", tagsJson);            
        model.addAttribute("tagViews", map.values());
    }
    
    
    public static void setCategoryViewsAttributes(List<CategoryView> views, Model model) throws Exception {
        model.addAttribute("views", views);
        
        Map<String, Long> map = views
                                    .stream()
                                    .limit(MAX_CATEGORIES)
                                    .collect(Collectors.toMap(CategoryView::getCategory, CategoryView::getCount, 
                                            (oldValue, newValue) -> newValue, LinkedHashMap::new));

        //This was overkill but I'm leaving it in place for reference
        /*Map<String, Long> map = views
                                    .stream()
                                    .limit(5)
                                    .collect(Collectors.groupingBy(CategoryView::getCategory, 
                                            Collectors.summarizingLong(CategoryView::getCount)))
                                    
                                    //we've got a Map<String, LongSummaryStatistics> - just get Map<String, Long>
                                    .entrySet()
                                    .stream()
                                    .collect(Collectors.toMap(Map.Entry::getKey, v -> v.getValue().getSum()))
                                    
                                    //reverse order by value
                                    .entrySet()
                                    .stream()
                                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))                         
                                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
          */
        
        //thymeleaf doesn't interpret the data set as strings, so 
        //we need to put it in JSON format
        String categoriesJson = MAPPER.writeValueAsString(map.keySet());
        model.addAttribute("categories", categoriesJson);            
        model.addAttribute("categoryViews", map.values());
    }
    
    
    public static void setViewsByDateAttributes(List<? extends BaseWebPageVisit> visits, Model model) throws Exception {
        Map<String, Long> map = visits
                                    .stream()
                                    .map(visit -> visit.getDateOfVisit())
                                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        
        Map<String, Long> sortedMap = map
                                        .entrySet()
                                        .stream()
                                        .sorted(Map.Entry.comparingByKey())                         
                                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                                (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        
        //thymeleaf doesn't interpret the data set as strings, so 
        //we need to put it in JSON format
        String datesJson = MAPPER.writeValueAsString(sortedMap.keySet());
        model.addAttribute("dates", datesJson);            
        model.addAttribute("visits", sortedMap.values());
    }
}
