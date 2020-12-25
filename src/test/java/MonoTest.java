import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

/**
 * @author bessam on 24/12/2020
 */

public class MonoTest {


    @Test
    void firstMono(){
        Mono.just("A").log().subscribe();
    }

    @Test
    void monoWithCunsomer(){
        Mono.just("A").log().subscribe(res -> System.out.println("result "+ res));

    }

    @Test
    void monoWithDoOn(){
        Mono.just("A")
                .log().doOnSubscribe(subscription -> System.out.println("Subscribed : "+subscription))
                .doOnRequest(request -> System.out.println("Request : "+ request))
                .doOnSuccess(sub-> System.out.println("Success : "+ sub))
                .subscribe(System.out::println);

    }

    @Test
    void emptyMono(){
        Mono.empty()
                .log()
                .subscribe(System.out::println,
                        null,
                        () ->System.out.println("Done"));

    }

    @Test
    void errorRunTimeExceptionError(){
        Mono
                .error(new RuntimeException())
                .log()
                .subscribe();

    }

    @Test
    void testExceptionMono(){
        Mono.error(new RuntimeException())
                .log()
                .subscribe(System.out::println,
                        e -> System.out.println("Error : "+e));

    }

    @Test
    void testDoOnErrorMono(){
        Mono.error(new RuntimeException())
                .doOnError(e -> System.out.println("Error: "+e))
                .log()
                .subscribe();

    }

    @Test
    void errorOnErrorResume(){
        //to return a Mono when an error acure
        Mono.error(new RuntimeException())
                .onErrorResume(e -> {
                    System.out.println("Error " + e);
                    return Mono.just("B");
                })
                .log()
                .subscribe();
    }

    @Test
    void errorOnErrorReturn(){
        //to return a value when an error accure
        Mono.error(new RuntimeException())
                .onErrorReturn("B")
                .log()
                .subscribe();
    }
}
