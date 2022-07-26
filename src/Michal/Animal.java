package Michal;

public class Animal {
    double  body_wt;
    double brain_wt;
    double non_dreaming;
    double dreaming;
    double total_sleep;
    double life_span;
    double gestation;
    int predation;
    int exposure;
    int danger;

    public Animal
            (double body_wt, double brain_wt, double non_dreaming, double dreaming,
             double total_sleep, double life_span, double gestation, int predation, int exposure, int danger)
    {
        this.body_wt = body_wt;
        this.brain_wt = brain_wt;
        this.non_dreaming = non_dreaming;
        this.dreaming = dreaming;
        this.total_sleep = total_sleep;
        this.life_span = life_span;
        this.gestation = gestation;
        this.predation = predation;
        this.exposure = exposure;
        this.danger = danger;
    }





    @Override
    public String toString() {
        return
                "{body_wt=" + body_wt +
                ", brain_wt=" + brain_wt +
                ", non_dreaming=" + non_dreaming +
                ", dreaming=" + dreaming +
                ", total_sleep=" + total_sleep +
                ", life_span=" + life_span +
                ", gestation=" + gestation +
                ", predation=" + predation +
                ", exposure=" + exposure +
                ", danger=" + danger +
                '}';
    }


}
