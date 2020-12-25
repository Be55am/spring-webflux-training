import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;
import java.util.Random;

/**
 * @author bessam on 24/12/2020
 */

public class FluxTest {

    @Test
    void createFlux() {
        Flux.just("A", "B", "C", "D")
                .log()
                .subscribe();
    }

    @Test
    void createFluxFromIterable() {
        // if u use just it will send all the list at once
        Flux.fromIterable(Arrays.asList("A", "B", "C", "D"))
                .log()
                .subscribe();
    }

    @Test
    void createFluxFromRage() {
        Flux.range(10, 10)
                .log()
                .subscribe();
    }

    @Test
    void createFluxFromInterval() throws InterruptedException {
        Flux.interval(Duration.ofSeconds(1))
                .log()
                .take(5)
                .subscribe();
        Thread.sleep(10000);
    }

    @Test
    void fluxRequest() {
        Flux.range(1, 10)
                .log()
                .subscribe(
                        null,
                        null,
                        null,
                        subscription -> subscription.request(3)
                );
    }

    /**
     * reqest 3 elements at first and then request a random number of elements each time
     */
    @Test
    void fluxCustomSubscriber() {
        Flux.range(1, 10)
                .log()
                .subscribe(new BaseSubscriber<Integer>() {
                    int elementsToProcess = 3;
                    int counter = 0;

                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        System.out.println("Subscribed !");
                        request(elementsToProcess);
                    }

                    @Override
                    protected void hookOnNext(Integer value) {
                        counter++;
                        if (counter == elementsToProcess) {
                            counter = 0;
                            Random r = new Random();
                            elementsToProcess = r.ints(1, 4).findFirst().getAsInt();
                            request(elementsToProcess);


                        }
                    }
                });
    }

    @Test
    void fluxlimitRate(){
        Flux.range(1, 5)
                .log()
                .limitRate(3)
                .subscribe();
    }
}
