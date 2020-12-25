import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * @author bessam on 25/12/2020
 */

public class OperatorTest {

    @Test
    void mapOperator() {
        Flux.range(1, 5)
                .map(i -> i * 10)
                .log()
                .subscribe();
    }

    /**
     * it does the same thing as map but it has to return a Flux or a mono (it transform the published elements into publishers)
     */
    @Test
    void flatMapOperator(){
        Flux.range(1, 5)
                .log()
                .flatMap(i -> Flux.range(i * 10, 2))
                .log()
                .subscribe();
    }

    @Test
    void flatMapMany(){
        Mono.just(3)
                .log()
                .flatMapMany(i -> Flux.range(1, i))
                .log()
                .subscribe();
    }

    @Test
    void concat() throws InterruptedException {
        Flux<Integer> oneToFive = Flux.range(1,5)
                .log()
                .delayElements(Duration.ofMillis(200));
        Flux<Integer> sixToTen = Flux.range(6,5)
                .log()
                .delayElements(Duration.ofMillis(400));
//        Flux.concat(oneToFive,sixToTen)
//                .log()
//                .subscribe();
        oneToFive.concatWith(sixToTen)
                .log()
                .subscribe();
        Thread.sleep(4000);

    }

    @Test
    void merge() throws InterruptedException {
        Flux<Integer> oneToFive = Flux.range(1,5)
                .log()
                .delayElements(Duration.ofMillis(200));
        Flux<Integer> sixToTen = Flux.range(6,5)
                .log()
                .delayElements(Duration.ofMillis(400));
        Flux.merge(oneToFive,sixToTen).map(x ->x * 10)
                .log()
                .subscribe();
        Thread.sleep(4000);
    }

    @Test
    void zip() throws InterruptedException {
        Flux<Integer> oneToFive = Flux.range(1,5)
                .log()
                .delayElements(Duration.ofMillis(200));
        Flux<Integer> sixToTen = Flux.range(6,5)
                .log()
                .delayElements(Duration.ofMillis(400));
        Flux.zip(oneToFive,sixToTen, (item1, item2) -> item1 + ", " + item2)
                .log()
                .subscribe();
        Thread.sleep(4000);

//        oneToFive.zipWith(sixToTen)
//                .subscribe();
    }
}
