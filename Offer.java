public class Offer {
    private String buying_club_id;
    private String selling_club_id;
    private String player_id;
    private long amount;
    private String id;
    private String target_id;
    private boolean rejected = false;
    private String date;

    public static final int OFFER_CREATED = 1;
    public static final int OFFER_PROCESSED = 2;

    public Offer(String buyer, String seller, String player, long amount, String target_id) {
        this.buying_club_id = buyer;
        this.selling_club_id = seller;
        this.player_id = player;
        this.amount = amount;
        this.id = CommonFunctions.getRandomId();
        this.target_id = target_id;
    }

    public String getSellingClubId() {
        return this.selling_club_id;
    }

    public Club getSellingClub() {
        return Sim.getClub(this.selling_club_id);
    }

    public String getBuyingClubId() {
        return this.buying_club_id;
    }

    public Club getBuyingClub() {
        return Sim.getClub(this.buying_club_id);
    }

    public String getPlayerId() {
        return this.player_id;
    }

    public Player getPlayer() {
        return Sim.getPlayer(this.player_id);
    }

    public long getAmount() {
        return this.amount;
    }

    public String getId() {
        return this.id;
    }

    public String getTargetId() {
        return this.target_id;
    }

    public void rejectOffer() {
        this.rejected = true;
    }

    public boolean isRejected() {
        return this.rejected;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return this.date;
    }
}
