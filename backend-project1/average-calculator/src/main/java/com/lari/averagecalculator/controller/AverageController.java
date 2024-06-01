package com.lari.averagecalculator.controller;

import com.lari.averagecalculator.service.NumberService;
import com.lari.averagecalculator.util.NumberUtils;
import com.lari.averagecalculator.window.SlidingWindow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/numbers")
public class AverageController {

    private final NumberService numberService;
    private final SlidingWindow slidingWindow;

    @Autowired
    public AverageController(NumberService numberService) {
        this.numberService = numberService;
        this.slidingWindow = new SlidingWindow(10);  // Window size is set to 10 here
    }

    @GetMapping("/{numberid}")
    public Response getAverage(@PathVariable String numberid) {
        List<Integer> fetchedNumbers = numberService.fetchNumbers(numberid);
        List<Integer> qualifiedNumbers = fetchedNumbers.stream()
                .filter(num -> isQualified(numberid, num))
                .distinct()
                .collect(Collectors.toList());

        List<Integer> windowPrevState = slidingWindow.getWindow();
        List<Integer> windowCurrState = slidingWindow.updateWindow(qualifiedNumbers);
        double average = slidingWindow.calculateAverage();

        return new Response(fetchedNumbers, windowPrevState, windowCurrState, average);
    }

    private boolean isQualified(String numberid, int n) {
        switch (numberid) {
            case "p":
                return NumberUtils.isPrime(n);
            case "f":
                return NumberUtils.isFibonacci(n);
            case "e":
                return NumberUtils.isEven(n);
            case "r":
                return NumberUtils.isRandom(n);
            default:
                return false;
        }
    }

    public static class Response {
        private final List<Integer> numbers;
        private final List<Integer> windowPrevState;
        private final List<Integer> windowCurrState;
        private final double avg;

        public Response(List<Integer> numbers, List<Integer> windowPrevState, List<Integer> windowCurrState, double avg) {
            this.numbers = numbers;
            this.windowPrevState = windowPrevState;
            this.windowCurrState = windowCurrState;
            this.avg = avg;
        }

        public List<Integer> getNumbers() {
            return numbers;
        }

        public List<Integer> getWindowPrevState() {
            return windowPrevState;
        }

        public List<Integer> getWindowCurrState() {
            return windowCurrState;
        }

        public double getAvg() {
            return avg;
        }
    }
}

