package bmw.poc.event;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@EqualsAndHashCode
@Getter  @Accessors(fluent = true)
@AllArgsConstructor  @NoArgsConstructor
public class Resposta {

    private Integer id;
    private String msg;

    private static Integer counter = 0;

    public static Resposta build() {
        return new Resposta(++counter, "Mensagem: " + counter);
    }


    @Override
    public String toString() {
        return "Resposta{" +
                "id=" + this.id +
                ", msg='" + this.msg + '\'' +
                '}';
    }

}
