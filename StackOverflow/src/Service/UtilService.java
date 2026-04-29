package Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class UtilService {

    private List<String> TAGS = List.of(
            "#java", "#python", "#cpp", "#javascript", "#typescript",
            "#golang", "#rust", "#kotlin", "#swift", "#ruby",
            "#php", "#scala", "#bash", "#shell", "#sql",
            "#nosql", "#mongodb", "#redis", "#postgresql", "#mysql",
            "#docker", "#kubernetes", "#devops", "#cloud", "#aws",
            "#azure", "#gcp", "#microservices", "#systemdesign", "#distributed",
            "#algorithms", "#datastructures", "#dsa", "#leetcode", "#competitiveprogramming",
            "#backend", "#frontend", "#fullstack", "#api", "#rest",
            "#graphql", "#webdev", "#ui", "#ux", "#testing",
            "#debugging", "#performance", "#security", "#networking", "#opensource"
    );
//black box service to extract tags from a question
    List<String> extractTags(String question){
        if(question == null || question.isBlank()){
            return List.of();
        }

        String normalizedQuestion = question.toLowerCase(Locale.ROOT);
        Set<String> extractedTags = new LinkedHashSet<>();

        for(String tag : TAGS){
            String keyword = tag.substring(1);
            if(normalizedQuestion.contains(tag) || normalizedQuestion.contains(keyword)){
                extractedTags.add(tag);
            }
        }

        return new ArrayList<>(extractedTags);

    }
}
