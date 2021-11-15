package homework.dataprocessor;

import homework.model.Measurement;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {
        //группирует выходящий список по name, при этом суммирует поля value
        Map<String, Double> resMap = data.stream()
                .collect(
                        Collectors.groupingBy(
                                Measurement::getName,
                                TreeMap::new,
                                Collectors.summingDouble(Measurement::getValue)
                        )
                );
        return resMap;
    }
}
