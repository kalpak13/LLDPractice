package Service;

import java.util.List;

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
    int min =0;
    int max = TAGS.size()-1;
//black box service to extract tags from a question
    List<String> extractTags(String question){
        int randomNum = (int)(Math.random() * (max - min + 1)) + min;
        int randomNum2 = (int)(Math.random() * (max - randomNum + 1)) + randomNum;
        return  TAGS.subList(randomNum, randomNum2);

    }
}
