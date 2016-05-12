Brief explanation:
	MainApplet.java:
		1.setUp():設置視窗大小；並load各個node和link的data；設置add all和clean的按鈕，是用controlP5的方式；設置中間大圓圈的位置座標、半徑、權重。
		2.buttonA():add所有小圓圈到大圓圈。
		3.buttonB():clean所有在大圓圈中的小圓圈。
		4.draw():設置背景；設置大圓圈；設置小圓圈以及當滑鼠移動到小圓圈時，小圓圈會放大還有顯示名字；設置小圓圈之間的線條。
		5.mousePressed():判斷抓哪個小圓圈。
		6.mouseDragged():設置小圓圈會跟著滑鼠移動，以及當拖小圓圈到大圓圈時，大圓圈的線會變粗。
		7.mouseReleased():判斷滑鼠放開小圓圈時的狀況，如果不是在大圓圈的範圍內，則會是在原本左邊的位置，如果是在大圓圈的範圍內，則會放到大圓圈上。
		8.setLittleCirclePosition():設置小圓圈放到大圓圈上的位置，方法是根據目前在大圓圈上的小圓圈數是幾個，在設置其相對於圓心的角度。
		9.loadData():load從JSON檔讀進來的node和link的資料，並加到各自的character上。
	Character.java:
		1.Character(MainApplet parent,String name,String colour,int x,int y):設置這個character的相關數據資料。
		2.addTarget(Character target):設置這個character的對方target相關的資料到array list。
		3.各個setter和getter:設置以及取得數據。
		4.display():設置小圓圈的顯示樣子與位置，

The problem:
	1.小圓圈要放到大圓圈上面的位置原本都會重疊不然就是亂跑。->Solution:重算一直設置看看。
	2.滑鼠要操控方面要注意蠻多小細節，有時候會常常出現很多bug。->Solution:一直debug。
	3.小圓圈或是小圓圈之間的資料以及狀態常常會漏掉或是沒有設好。->Solution:加條件、debug。
	4.線條不會畫弧線所以畫直線。->Solution:沒有解決ＱＱ
	