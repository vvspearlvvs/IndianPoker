package Model;

import IO.Database;

public class IndianPoker {
	private static IndianPoker uniqueInstance; //싱글톤

	public Player p1;
	public Player p2;
	public static Database db;
	public int currentBP; //현재 게임에 걸려있는 베팅 포인트

	private IndianPoker(){	
		db = new Database();
		currentBP = 0;
	}
	
	public static IndianPoker getInstance(){
		if(uniqueInstance == null){
			uniqueInstance = new IndianPoker();
		}
		return uniqueInstance;
	}
	
	public void start(){
		int turn = 0;
		
		while(true){
			if(turn == 10){
				System.out.println("턴이 모두 진행되었으므로 게임을 종료합니다.");
				break;
			}
			if(p1.point == 0){
				System.out.println("Player1의 점수가 0점이 되어 게임을 종료합니다.");
				break;
			}
			if(p2.point == 0){
				System.out.println("Player2의 점수가 0점이 되어 게임을 종료합니다.");
				break;
			}
			turn++;
			System.out.println("[Turn : "+turn+"]");
			p1.card.selectCard();
			p2.card.selectCard();
			
			while(true){
				p1.Betting();
				p2.Betting();
				
				currentBP += p1.betPoint + p2.betPoint; //현재 이 게임에 걸린 베팅점수
				
				System.out.println("Player1 카드번호 : "+p1.card.cardValue+", Player1 베팅점수 : "+p1.betPoint); //콘솔에서 잘되나 확인차 출력
				System.out.println("Player2 카드번호 : "+p2.card.cardValue+", Player2 베팅점수 : "+p2.betPoint); //실제로는 보여주면 안되고 베팅점수만
				
				if(p1.betPoint == 0 || p2.betPoint == 0){
					GiveUp();
					break;
				}
				
				if(p1.betPoint == p2.betPoint){
					compare();
					break;
				}
			}
			System.out.println(p1.name + "님의 남은 포인트는 " + p1.point + "이고, " + p1.win + "번 이겼습니다.");
			System.out.println(p2.name + "님의 남은 포인트는 " + p2.point + "이고, " + p2.win + "번 이겼습니다.");
			System.out.println("------------------------------------------------------");
		}
	}
	
	public void GiveUp(){
		if(p1.betPoint == 0 && p2.betPoint == 0){ //둘다 포기한 경우
			if(p1.card.cardValue == 10){
				p1.point -= 100; //Player1이 포기했는데 카드번호가 10인 경우 100점 차감
				currentBP += 100;
			}
			if(p2.card.cardValue == 10){
				p2.point -= 100; //Player2가 포기했는데 카드번호가 10인 경우 100점 차감
				currentBP += 100;
			}
		}else{
			if(p1.betPoint == 0){
				if(p1.card.cardValue == 10){
					p1.point -= 100; //Player1이 포기했는데 카드번호가 10인 경우 100점 차감
					currentBP += 100;
					p2.point += currentBP;
					currentBP = 0;
				}
			}
			if(p2.betPoint == 0){
				if(p2.card.cardValue == 10){
					p2.point -= 100; //Player2가 포기했는데 카드번호가 10인 경우 100점 차감
					currentBP += 100;
					p1.point += currentBP;
					currentBP = 0;
				}
			}
		}
		
	}
	public void compare(){
		if(p1.card.cardValue > p2.card.cardValue){ //Player1 승리
			System.out.println("Player1의 승리");
			p1.point += currentBP;
			currentBP = 0;
			p1.win++;
		}else if(p1.card.cardValue < p2.card.cardValue){ //Player2 승리
			System.out.println("Player2의 승리");
			p2.point += currentBP;
			currentBP = 0;
			p2.win++;
		}
		//무승부인 경우 아무것도 하지 않음
		System.out.println("무승부");
	}
}