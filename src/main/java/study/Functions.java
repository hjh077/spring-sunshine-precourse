package study;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import study.dto.AddDayRequest;
import study.dto.DateResponse;

import java.time.LocalDate;
import java.util.function.Function;

@Configuration
public class Functions {

    @Bean
    @Description("Calculate a date after adding days from today")
    public Function<AddDayRequest, DateResponse> addDaysFromToday() {
        return request -> {
            LocalDate result = LocalDate.now().plusDays(request.days());
            return new DateResponse(result.toString());
        };
    }

}
