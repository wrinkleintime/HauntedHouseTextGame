import java.util.Random;

public class Fight
{
	private static Random random = new Random();

	public static String fight(Player currentPlayer, Room currentRoom) {
		Monster currentMonster = currentRoom.getMonster();

		System.out.println(currentMonster.getName() + " is coming!!!!");
		currentMonster.printMonster();

		currentPlayer.checkStatus();
		currentMonster.checkStatus();

		while (currentMonster.getCurrentHealth() > 0) {
			String toFlee = "";

			while (!toFlee.equals("FIGHT") && !toFlee.equals("FLEE")) {
				toFlee = UI.promptLine("Do you want to FIGHT or FLEE?").toUpperCase();
				System.out.println("-------------------");
			}

			if (toFlee.equals("FLEE")) {
				if (random.nextBoolean()) {
					System.out.println("You successfully fled from the monster. You're back in the hallway.");
					return "fled";
				} else {
					System.out.println("You slipped and fell, and the monster got a second attack.");
					if (damageTaken(currentPlayer, currentMonster)) {
						return "dead";
					}
				}
			}

			if (toFlee.equals("FIGHT")) {
				if (damageTaken(currentPlayer, currentMonster)) {
					return "dead";
				}
			}

			if (currentPlayer.getHealth() < 5) {
				System.out.println("Your health is really low.");
			}
		}

		System.out.println("------------------fight over-------------------------");
		System.out.println(currentMonster.getName() + " was defeated!");
		currentRoom.killMonster();
		currentPlayer.checkStatus();
		return "won";
	}

	private static boolean damageTaken(Player currentPlayer, Monster currentMonster){
		int damageGiven = random.nextInt(currentPlayer.getCurrentWeapon().getAttribute());
		int damageSustained = currentMonster.getCurrentStrength();

		currentMonster.setCurrentHealth(currentMonster.getCurrentHealth() - damageGiven);
		currentPlayer.incrementHealth(-damageSustained);

		System.out.println("You took " + damageSustained + " damage and the monster took " + damageGiven + " damage.");

		if (currentPlayer.getHealth() <= 0)
		{
			System.out.println("You're dead!");
			return true;
		}
		System.out.println("Your health is now " + currentPlayer.getHealth() + "\nand the monster's health is now " + currentMonster.getCurrentHealth());
		System.out.println("-----------------------");
		return false;
	}
}