package org.duckstudy.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.doAnswer;

import java.util.ArrayList;
import java.util.List;
import org.duckstudy.movingcar.Car;
import org.duckstudy.movingcar.CarMover;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@DisplayName("자동차 게임 테스트")
class CarRacerTest {

    @InjectMocks
    private CarRacer carRacer;
    @Mock
    private CarMover carMover;

    private final String[] nameList = {"1번째 자동차", "2번째 자동차", "3번째 자동차"};

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        carRacer = new CarRacer(carMover, 3, 5, nameList);
    }

    @Test
    @DisplayName("게임 종료 시 우승자를 반환한다")
    void calculateWinnerWhenGameEnds() {
        doAnswer(invocation -> {
            List<Car> carList = invocation.getArgumentAt(0, List.class);
            carList.stream().filter(c -> c.getName().equals(nameList[0]) || c.getName().equals(nameList[1]))
                    .forEach(Car::move);
            return null;
        }).when(carMover).move(anyListOf(Car.class));

        ArrayList<Car> winnerList = carRacer.play();

        assertThat(winnerList.size()).isEqualTo(2);
        assertThat(winnerList.get(0).getName()).isEqualTo(nameList[0]);
        assertThat(winnerList.get(1).getName()).isEqualTo(nameList[1]);
    }
}
