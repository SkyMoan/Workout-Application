//Each layout will have its own location

package all;

public class LayoutHandler {

	// panes common for both users and coaches
	public static final String preloaderPane = "layouts/common/Preloader.fxml";
	public static final String loginPane = "layouts/common/Login.fxml";
	public static final String signUpPane = "layouts/common/SignUp.fxml";
	public static final String inboxPane = "layouts/common/Inbox.fxml";

	// user panes
	public static final String mainUserPane = "layouts/user/MainMenu.fxml";
	public static final String newWorkoutPane = "layouts/user/Workout.fxml";
	public static final String coachesPane = "layouts/user/Coaches.fxml";
	public static final String historyPane = "layouts/user/History.fxml";
	public static final String statisticsPane = "layouts/user/Statistics.fxml";

	// coach panes
	public static final String mainCoachPane = "layouts/coach/CoachMenu.fxml";
	public static final String clientsPane = "layouts/coach/CoachClients.fxml";

}
