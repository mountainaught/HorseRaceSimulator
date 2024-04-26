package horserace;

public class startRace {
    public static void main(String[] args) {
        Race race = new Race(10);

        Horse horseUno = new Horse('♘', "nightmare", 0.4);
        Horse catCorePlaylist = new Horse('♞', "despair", 0.2);
        Horse JohnQueenMary = new Horse('h', "eduardo", 0.4);

        race.addHorse(horseUno, 1);
        race.addHorse(catCorePlaylist, 2);
        race.addHorse(JohnQueenMary, 3);

        race.startRace();
    }

}