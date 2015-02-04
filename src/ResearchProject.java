import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.swt.*;
import org.eclipse.swt.browser.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;


import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;




public class ResearchProject 
{
//Declare the data structures I need	
	static Vector<Vector<String>> vbmVector = new Vector<Vector<String>>();
	static ArrayList<VisualBlock> allVbmList = new ArrayList<VisualBlock>();
	static ArrayList<ConcurrentHashMap<VisualBlock, ArrayList<VisualBlock>>> candidates = new ArrayList<ConcurrentHashMap<VisualBlock,ArrayList<VisualBlock>>>();
	static ArrayList<ConcurrentHashMap<VisualBlock, ArrayList<VisualBlock>>> allGroupsByWidth = new ArrayList<ConcurrentHashMap<VisualBlock, ArrayList<VisualBlock>>>();
	static ConcurrentHashMap<VisualBlock, ArrayList<VisualBlock>> blocksWithChildren = new ConcurrentHashMap<VisualBlock, ArrayList<VisualBlock>>();

	//Function that checks whether two container blocks have similar widths
	public static boolean checkSimilarByWidth(Map.Entry<VisualBlock, ArrayList<VisualBlock>> block1, Map.Entry<VisualBlock, ArrayList<VisualBlock>> block2)
	{
		double widthDifference = block1.getKey().getWidth() - block2.getKey().getWidth();
		
		if((widthDifference <= 3 && widthDifference >= -3))//THRESHOLD HARDCODED
		{	
			return true;
		}
		
		return false;
	}
	
	//function that checks if a block has any children. It returns an array of children
	public static ArrayList<VisualBlock> getChildren (VisualBlock block)
	{
		ArrayList<VisualBlock> childrenList = new ArrayList<VisualBlock>();
		
		if(block!=null)
		{
			if(block.getWidth()!=null && block.getBottomOffset() >0 && block.getTopOffset() >30 && block.getLeftOffset()> 0 && block.getRightOffset() >0 && block.getWidth()>0)
			{	
				for(int i=0; i<allVbmList.size(); i++)
				{
					if((allVbmList.get(i).getBottomOffset()+1) <= block.getBottomOffset() && (allVbmList.get(i).getTopOffset()-1) >= block.getTopOffset() && (allVbmList.get(i).getLeftOffset() + 1) >= block.getLeftOffset() && (allVbmList.get(i).getRightOffset()-1) <= block.getRightOffset())
					{
						childrenList.add(allVbmList.get(i));
					}
				}	
			}
		}
		
		return childrenList;
	}

	//Function that draws all containers and basic blocks for the final candidate group
	public static void drawBestCandidate(ConcurrentHashMap<VisualBlock, ArrayList<VisualBlock>> biggest, Browser browser)
	{
		for (Map.Entry<VisualBlock, ArrayList<VisualBlock>> blocks : biggest.entrySet())
		 {
			 VisualBlock currentKey = blocks.getKey();
			 ArrayList<VisualBlock> currentVals = blocks.getValue();
			 
				Double containertop    = currentKey.getTopOffset();
				Double containerright  = currentKey.getRightOffset();
				Double containerbottom = currentKey.getBottomOffset();
				Double containerleft   = currentKey.getLeftOffset();

				String drawBoxStyleContainer;
				
				
				drawBoxStyleContainer = vbm.blockDrawString("blue",containertop,containerright,containerbottom,containerleft,"");	
				
				boolean drawBoxStyleExeContainer = browser.execute(drawBoxStyleContainer);
				if(!drawBoxStyleExeContainer)
					System.out.println();
			 
			for(int j=0; j<currentVals.size(); j++)
			{
				Double top    = currentVals.get(j).getTopOffset();
				Double right  = currentVals.get(j).getRightOffset();
				Double bottom =currentVals.get(j).getBottomOffset();
				Double left   = currentVals.get(j).getLeftOffset();

				String drawBoxStyle;
				
			
				drawBoxStyle = vbm.blockDrawString("red",top,right,bottom,left,"");
				
				
				boolean drawBoxStyleExe = browser.execute(drawBoxStyle);
				if(!drawBoxStyleExe)
					System.out.println();
			}
			 
		 }
	}

//Function that draws all blocks
 public static void drawAll(ArrayList<VisualBlock> all, Browser browser)
{

	for(int i=0; i<all.size();i++)
	{
		if(all.get(i).getTopOffset()!=null && all.get(i).getBottomOffset()!=null && all.get(i).getRightOffset()!=null && all.get(i).getLeftOffset()!=null)
		{
		Double containertop    = all.get(i).getTopOffset();
		Double containerright  = all.get(i).getRightOffset();
		Double containerbottom = all.get(i).getBottomOffset();
		Double containerleft   = all.get(i).getLeftOffset();
		
	
	String drawBoxStyleContainer;
	
	
	drawBoxStyleContainer = vbm.blockDrawString("blue",containertop,containerright,containerbottom,containerleft,"");	
	
	boolean drawBoxStyleExeContainer = browser.execute(drawBoxStyleContainer);
	if(drawBoxStyleExeContainer)
	{}
	else
		System.out.println("NO DRAW");
	
		}
	}
		
	
}

 //Draws all groups by their container
 public static void drawAllGroupsContainerOnly(ArrayList<ConcurrentHashMap<VisualBlock, ArrayList<VisualBlock>>>allGroups, Browser browser)
 {
 	
 	for(int i=0; i<allGroups.size(); i++)
 	{						
 		
 		ConcurrentHashMap<VisualBlock, ArrayList<VisualBlock>> currentMap = allGroups.get(i);
 		
 		if (currentMap.size() > 1)
 		{
 		for (Map.Entry<VisualBlock, ArrayList<VisualBlock>> blocks : currentMap.entrySet())
 		 {
 			 VisualBlock currentKey = blocks.getKey();
 			 ArrayList<VisualBlock> currentVals = blocks.getValue();
 		/*	 
 			for(int j=0; j<currentVals.size(); j++)
 			{
 					Double top    = currentVals.get(j).getTopOffset()+1;
 					Double right  = currentVals.get(j).getRightOffset()-1;
 					Double bottom =currentVals.get(j).getBottomOffset()-1;
 					Double left   = currentVals.get(j).getLeftOffset()+1;
 	
 					String drawBoxStyle;
 					
 				
 					drawBoxStyle = vbm.blockDrawString("red",top,right,bottom,left);
 					
 					
 					boolean drawBoxStyleExe = browser.execute(drawBoxStyle);
 					if(!drawBoxStyleExe)
 						System.out.println();
 					else {
 						currentVals.get(j).setisDrawn(true);
 					}
 				
 			}
 			*/
 			
 				Double containertop    = currentKey.getTopOffset()-1;
 				Double containerright  = currentKey.getRightOffset()+1;
 				Double containerbottom = currentKey.getBottomOffset()+1;
 				Double containerleft   = currentKey.getLeftOffset()-1;
 	
 				String drawBoxStyleContainer="";
 				
 				
 				//Manually set the colour depending on how many groups I get on average.
 				if(i==0)
 				drawBoxStyleContainer = vbm.blockDrawString("blue",containertop,containerright,containerbottom,containerleft,"");
 				else if(i==1)
 					drawBoxStyleContainer = vbm.blockDrawString("red",containertop,containerright,containerbottom,containerleft,"");
 				else if(i==2)
 					drawBoxStyleContainer = vbm.blockDrawString("yellow",containertop,containerright,containerbottom,containerleft,"");
 				else if(i==3)
 					drawBoxStyleContainer = vbm.blockDrawString("green",containertop,containerright,containerbottom,containerleft,"");
 				else if(i==4)
 					drawBoxStyleContainer = vbm.blockDrawString("brown",containertop,containerright,containerbottom,containerleft,"");
 				else if(i==5)
 					drawBoxStyleContainer = vbm.blockDrawString("chocolate",containertop,containerright,containerbottom,containerleft,"");
 				else if(i==6)
 					drawBoxStyleContainer = vbm.blockDrawString("lime",containertop,containerright,containerbottom,containerleft,"");
 				else if(i==7)
 					drawBoxStyleContainer = vbm.blockDrawString("orangeRed",containertop,containerright,containerbottom,containerleft,"");
 				else if(i==8)
 					drawBoxStyleContainer = vbm.blockDrawString("RoyalBlue",containertop,containerright,containerbottom,containerleft,"");
 				else if(i==9)
 					drawBoxStyleContainer = vbm.blockDrawString("SpringGreen",containertop,containerright,containerbottom,containerleft,"");
 				else if(i==10)
 					drawBoxStyleContainer = vbm.blockDrawString("Teal",containertop,containerright,containerbottom,containerleft,"");
 				else if(i==11)
 					drawBoxStyleContainer = vbm.blockDrawString("wheat",containertop,containerright,containerbottom,containerleft,"");
 				else if(i==12)
 					drawBoxStyleContainer = vbm.blockDrawString("thistle",containertop,containerright,containerbottom,containerleft,"");
 				else if(i==13)
 					drawBoxStyleContainer = vbm.blockDrawString("Salmon",containertop,containerright,containerbottom,containerleft,"");
 				else if(i==14)
 					drawBoxStyleContainer = vbm.blockDrawString("Pink",containertop,containerright,containerbottom,containerleft,"");
 				else if(i==15)
 					drawBoxStyleContainer = vbm.blockDrawString("gray",containertop,containerright,containerbottom,containerleft,"");
 				else if(i==16)
 					drawBoxStyleContainer = vbm.blockDrawString("blue",containertop,containerright,containerbottom,containerleft,"");
 				else if(i==17)
 					drawBoxStyleContainer = vbm.blockDrawString("blue",containertop,containerright,containerbottom,containerleft,"");
 				else if(i==18)
 					drawBoxStyleContainer = vbm.blockDrawString("blue",containertop,containerright,containerbottom,containerleft,"");
 				else if(i==19)
 					drawBoxStyleContainer = vbm.blockDrawString("blue",containertop,containerright,containerbottom,containerleft,"");
 				else if(i==20)
 					drawBoxStyleContainer = vbm.blockDrawString("blue",containertop,containerright,containerbottom,containerleft,"");
 				else if(i==21)
 					drawBoxStyleContainer = vbm.blockDrawString("blue",containertop,containerright,containerbottom,containerleft,"");
 				else if(i==22)
 					drawBoxStyleContainer = vbm.blockDrawString("blue",containertop,containerright,containerbottom,containerleft,"");
 				else if(i==23)
 					drawBoxStyleContainer = vbm.blockDrawString("blue",containertop,containerright,containerbottom,containerleft,"");
 				else if(i==24)
 					drawBoxStyleContainer = vbm.blockDrawString("blue",containertop,containerright,containerbottom,containerleft,"");
 				
 				
 				boolean drawBoxStyleExeContainer = browser.execute(drawBoxStyleContainer);
 				if(!drawBoxStyleExeContainer)
 					System.out.println();
 				else {
 					currentKey.setisDrawn(true);
 				
 			}
 			 
 		 }
 		}
 	}
 }
 
 //Recursively gets all basic blocks for a block
 public static ArrayList<VisualBlock> getAllBasicBlocks(ArrayList<VisualBlock> blocks)
 {
 	ArrayList<VisualBlock> basicBlocks = new ArrayList<VisualBlock>();
 	
 	for(int i=0; i<blocks.size(); i++)
 	{
 		if(blocks.get(i).getisBasic())
 		{
 			basicBlocks.add(blocks.get(i));
 		}
 		
 		else if(!blocks.get(i).getisBasic())
 		{
 			ArrayList<VisualBlock> currentChildrenArrayList = getChildren(blocks.get(i));
 			basicBlocks.addAll(getAllBasicBlocks(currentChildrenArrayList));
 		}
 	}

 	return basicBlocks;
 }
 
 //checks if the key (i.e. container block) of a container map is conained inside another one.
 public static boolean contains(Map.Entry<VisualBlock, ArrayList<VisualBlock>> block1, Map.Entry<VisualBlock, ArrayList<VisualBlock>> block2)
 {
 	
 	if((block1.getKey().getBottomOffset()+1) <= block2.getKey().getBottomOffset() && (block1.getKey().getTopOffset()-1) >= block2.getKey().getTopOffset() && (block1.getKey().getLeftOffset() + 1) >= block2.getKey().getLeftOffset() && (block1.getKey().getRightOffset()-1) <= block2.getKey().getRightOffset())
 	{
 		return true;
 	}
 	
 	return false;
 }
 
 //checks children content similarity for two parent blocks.
 public static boolean checkContentSimilarity(Map.Entry<VisualBlock, ArrayList<VisualBlock>> block1, Map.Entry<VisualBlock, ArrayList<VisualBlock>> block2) 
 {
 	//get children for the two blocks
 	ArrayList<VisualBlock> values1 = block1.getValue();
 	ArrayList<VisualBlock> values2 = block2.getValue();

 	boolean returnVal = false;
 	
 	int matchCount =0;
 	
 	int block1ChildrenSize, block2ChildrenSize;
 	
 	//if either block is contained in the other then they are not "similar"
	if(contains(block1,block2) || contains(block2, block1))
 	{
 		return false;
 	}
 	
 	//declare the list which will hold all basic blocks for the 2 blocks being compared
 	ArrayList<VisualBlock> block1Basic;
 	ArrayList<VisualBlock> block2Basic;
	 		
 		//Recursively get all basic blocks 
 		block1Basic = getAllBasicBlocks(values1);
 		block2Basic = getAllBasicBlocks(values2);
 		
 		 block1ChildrenSize =  block1Basic.size();
 	     block2ChildrenSize =  block2Basic.size();	
 			  
 			  
 		//Go through each basic block of the first list and try to find a match for it in the second list
 		for(int i=0; i<block1Basic.size();i++)
 		{
 			VisualBlock current1 = block1Basic.get(i);
 			for (int j=0; j<block2Basic.size(); j++)
 			{
 				VisualBlock current2 = block2Basic.get(j);

 					//If a match is found keep count of it and remove the two matched blocks from the list still to compare
 					if(current1.getfontFamily().equals(current2.getfontFamily()) && current1.getfontSize().equals(current2.getfontSize()) 
 							&& current1.getfontStyle().equals(current2.getfontStyle()) && current1.getfontWeight().equals(current2.getfontWeight()) 
 							&& current1.getlineHeight().equals(current2.getlineHeight()) && current1.gettextAlign().equals(current2.gettextAlign())
 						    && current1.getdisplay().equals(current2.getdisplay()) && current1.getclear().equals(current2.getclear()))
 					{
 						matchCount++;
 						block1Basic.remove(i);
 						block2Basic.remove(j);
 						break;
 					}
 			}
 		}
 	
 	
 	float similarityMargin = (float) matchCount/ (float)block1ChildrenSize;
 	
 	//If you have matched exactly every block or there's a difference of one then consider the two blocks similar
 	if(matchCount == block1ChildrenSize || similarityMargin>=0.51)//THRESHOLD SET MANUALLY
 		returnVal=true;
 	
 	return returnVal;		
 }
 
 //Utility function to check if two visual blocks overlap
 public static boolean Overlap(VisualBlock block1, VisualBlock block2)
 {
	 if(block1.getTopOffset()==block2.getTopOffset() && block2.getRightOffset() == block1.getRightOffset() && block2.getBottomOffset() == block1.getBottomOffset() && block1.getLeftOffset() == block2.getLeftOffset())
	 {
		 return true;
	 }
	 
	 return false;
 }
 
 //Simple Utility to check if two visual block object contain eachother
 public static boolean containsSimple(VisualBlock block1, VisualBlock block2)
 {
	 if(block1.getTopOffset()==block2.getTopOffset() && block2.getRightOffset() == block1.getRightOffset() && block2.getBottomOffset() == block1.getBottomOffset() && block1.getLeftOffset() == block2.getLeftOffset())
	 {
		 return false;
	 }
	 
	 else if(block1.getLeftOffset() <= (block2.getLeftOffset()+1) && (block2.getRightOffset()-1) <= block1.getRightOffset() && (block2.getTopOffset()+1) >= block1.getTopOffset() && block1.getBottomOffset() >= (block2.getBottomOffset()-1))
	 	{
	 		return true;
	 	}
	 	
	 	return false;
 }
 
 //function that counts only the data records at the end when the group of candidates is created
 public static int countElements(ConcurrentHashMap<VisualBlock, ArrayList<VisualBlock>> entry)
 {
	 int count = 0;
	 
	 
	 //Make sure a container doesnt contain "too many" things
	 for(int i=0; i< entry.keySet().toArray().length; i++)
		{
		 int containsSafety=0;
		
		 for(int j=0; j< entry.keySet().toArray().length;j++)
			{	
				if(containsSimple((VisualBlock)entry.keySet().toArray()[i],(VisualBlock)entry.keySet().toArray()[j]))
				{
					containsSafety++;
				}
			}
			
		 //HARD CODED THRESHOLD
			if(containsSafety>=5){
				entry.keySet().remove(entry.keySet().toArray()[i]);	
			}
		} 
	 
	//Only count things that arent contained in anything else 
	for(int i=0; i< entry.keySet().toArray().length; i++)
	{
		boolean contained = false;
		for(int j=0; j< entry.keySet().toArray().length;j++)
		{	
			if(containsSimple((VisualBlock)entry.keySet().toArray()[j],(VisualBlock)entry.keySet().toArray()[i]))
			{
				contained = true;
			}
		}
		
		if(!contained)
			count++;	
	}
	 
	
	 return count;
 }
 
	public static void main (String [] args) {
		final String html = "<html><head></head><title>Snippet</title><body><p id='myid'>Best Friends</p><p id='myid2'>Cat and Dog</p>text<ul><li>foo</li><li>bar</li></ul></body></html>";
		Display display = new Display ();
		Shell shell = new Shell (display);
		shell.setLayout (new FillLayout ());
		shell.setBounds (10,20,1600,1000);
		
		final Browser browser;
		
		try {
			browser = new Browser (shell, SWT.NONE);
		} catch (SWTError e) {
			System.out.println ("Could not instantiate Browser: " + e.getMessage ());
			display.dispose();
			return;
		}
				
		if (browser != null) {browser.setUrl("http://www.watchshop.com/Adidas-Watches.html&st=adidas");}
		//if (browser != null) {browser.setUrl("http://www.screwfix.com/c/screws-nails-fixings/bolt-type-shield-anchor/cat840436");}
		//if (browser != null) {browser.setUrl("http://www.game.co.uk/en/games/nintendo-wii/?attributeName1=Shop%20By&sortColumn=popular&sortTypeStr=DESC&inStockOnly=true&listerOnly=true&provenance=Preowned&attributeValue2=4294967256&attributeValue1=64&attributeName2=Catalog_GameSalesCatalog_EN_GB&cm_sp=topnav-_-Wii-_-POWiiGames");}
		//if (browser != null) {browser.setUrl("http://www.argos.co.uk/static/Search/searchTerms/BRACELET.htm");}
		//if (browser != null) {browser.setUrl("http://www.amazon.co.uk/s/ref=nb_sb_noss_1?url=search-alias%3Daps&field-keywords=tv&rh=i%3Aaps%2Ck%3Atv");}
		
		/**
		 * Takes LONG time
		 */
		//if (browser != null) {browser.setUrl("http://www.ebay.co.uk/sch/i.html?_odkw=furniture&_osacat=0&_from=R40&_trksid=p2045573.m570.l1313.TR10.TRC0&_nkw=dress&_sacat=0");}
		
		/***
		 * Example edge cases not working properly below 
		 *  ***/
		//if(browser != null) {browser.setUrl("http://www.newegg.com/DSLR-Cameras/SubCategory/ID-784");}
		//if(browser != null) {browser.setUrl("http://www.walmart.com/browse/computers/laptop-computers/3944_3951_132960/?_refineresult=true&facet=laptop_screen_size%3A15%60%60+-+16%60%60&ic=32_0&path=0%3A3944&povid=cat1089430-env432682-moduleA112112-lLinkLHNShopbySize4FifteentoSixteenInchLaptops");}
		
		final BrowserFunction function = new CustomFunction (browser, "theJavaFunction");
		 
		//check if JQuery is running
		 browser.addProgressListener (new ProgressAdapter () {
				public void completed (ProgressEvent event) {
					
					System.out.println("This happens after the shell is open.");
							
					//Push JQuery into the webpage
					String googleJQ = "(function() {var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true; ga.src = 'http://code.jquery.com/jquery-latest.js'; (document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(ga); })(); ";
					boolean injectGoogleJQ = browser.execute(googleJQ);
					if (!injectGoogleJQ) {System.out.println("Google JQ Script was not executed.");} else {/*System.out.println("Google JQ Script *was* executed.");*/}			
								
					//1 second delay to check if JQuery is running
					String s4 = "setTimeout(function(){if (typeof jQuery == 'undefined') {theJavaFunction('Not Loaded');} else {theJavaFunction('LOADED');  window.status = 'JQLOADED';}},3000); ";						
					//static String SCRIPT01 = "document.onmouseup = function() {if (window.getSelection) { window.status = 'SCRIPT01'+window.getSelection(); } else if (document.getSelection) { window.status = 'SCRIPT01'+document.getSelection(); } else if (document.selection) { window.status = 'SCRIPT01'+document.selection.createRange().text; }; window.status='';}"; 
					
					boolean injectResult4 = browser.execute(s4);
					if (!injectResult4) {System.out.println("Check JS Script was not executed.");} else {/*System.out.println("Check JS Script *was* executed.");*/}
		
				}
				
				public void changed(ProgressEvent event) { 					
				} 
			});
		 
		 //When the browser is ready injecy javascript
		 browser.addStatusTextListener(new StatusTextListener() { 				
				public void changed(StatusTextEvent event) { 
					
					if (event.text.startsWith("JQLOADED")) { 
						System.out.println("This is Java calling. I hear that JQuery has finally loaded. Now I can call the next function...");
										
					
						////// Use JQuery to get VBM	
						System.out.println("About to get VBM JQuery");			
						String s3 = "$('*').each(function(index) { 							"+			
								"var widthValue = $(this).width();							"+ 
								"var heightValue = $(this).height();						"+
								"var offset = $(this).offset(); 							"+
								"var offsetLeftValue = offset.left;							"+
								"var offsetTopValue = offset.top;							"+
								"var outerWidthValue = $(this).outerWidth();				"+
								"var outerHeightValue = $(this).outerHeight();				"+
								"var offsetRightValue = offsetLeftValue+outerWidthValue;	"+
								"var offsetBottomValue = offsetTopValue+outerHeightValue;	"+
								"var blockInnerText = '';									"+
								"var blockInnerText = $(this).text();						"+
								"var fontSize = '';										    "+
								"var fontSize = $(this).css('font-size');					"+
								"var fontWeight = '';									    "+
								"var fontWeight = $(this).css('font-weight');				"+
								"var fontFamily = '';									    "+
								"var fontFamily = $(this).css('font-family');				"+
								"var fontStyle = '';									    "+
								"var fontStyle = $(this).css('font-style');	  		        "+
								"var color = '';								     	    "+
								"var color = $(this).css('color');	  		                "+
								"var isVis = '';				                         	"+
								"isVis = $(this).is(':visible');		     		     	"+
								"var lineHeigh = '';										    "+
								"var lineHeigh = $(this).css('line-height');					"+
								"var borderColor = '';										    "+
								"var borderColor = $(this).css('border-color');					"+
								"var borderWidth = '';										    "+
								"var borderWidth = $(this).css('border-width');					"+
								"var borderStyle = '';										    "+
								"var borderStyle = $(this).css('border-style');					"+
								"var textDecoration = '';										    "+
								"var textDecoration = $(this).css('text-decoration');					"+
								"var padding = '';										    "+
								"var padding = $(this).css('padding');					"+
								"var background = '';										    "+
								"var background = $(this).css('background');					"+
								"var visibility = '';										    "+
								"var visibility = $(this).css('visibility');					"+
								"var TextAlign = $(this).css('text-align');		"+				
								"var Display = $(this).css('display');			"+				
								"var Clear = $(this).css('clear');                 "+
								"theJavaFunction(widthValue, heightValue, offsetTopValue, offsetRightValue, offsetBottomValue, offsetLeftValue, fontSize, fontWeight, fontFamily, fontStyle, color, isVis, lineHeigh, borderColor, borderWidth, borderStyle, textDecoration, padding, background, visibility, TextAlign, Display, Clear);	"+
								"});";										
						
						//Inject results 
						boolean injectResult3 = browser.execute(s3);
						if (!injectResult3) {System.out.println("Script was not executed.");} else {System.out.println("Script *was* executed.");}					
						System.out.println("JQuery has got the VBM");

						
						//Once the VBM is obtained, populate my own VisualBlock data structure
						for(int i=0; i<vbmVector.size(); i++)
						{
							VisualBlock thisBlock = new VisualBlock();
							
							for (int j = 0; j < vbmVector.get(i).size(); j++)
							{
								//Depending in what order I pass the arguments in the Javafunction in the JQuery above, I know exactly which argument is what
								if(!vbmVector.get(i).get(j).equals("LOADED"))
								{
								switch (j) {
								case 0: thisBlock.setWidth((vbmVector.get(i).get(j)!=null) ? Double.parseDouble(vbmVector.get(i).get(j).toString()) : 0);
									break;
								case 1: thisBlock.setHeight((vbmVector.get(i).get(j)!=null) ? Double.parseDouble(vbmVector.get(i).get(j).toString()) : 0);
									break;
								case 2: thisBlock.setTopOffset((vbmVector.get(i).get(j)!=null) ? Double.parseDouble(vbmVector.get(i).get(j).toString()) : -1);
									break;
								case 3: thisBlock.setRightOffset((vbmVector.get(i).get(j)!=null) ? Double.parseDouble(vbmVector.get(i).get(j).toString()) : -1);
									break;
								case 4: thisBlock.setBottomOffset((vbmVector.get(i).get(j)!=null) ? Double.parseDouble(vbmVector.get(i).get(j).toString()) : -1);
									break;
								case 5: thisBlock.setLeftOffset((vbmVector.get(i).get(j)!=null) ? Double.parseDouble(vbmVector.get(i).get(j).toString()) : -1);
									break;
								case 6: thisBlock.setfontSize((vbmVector.get(i).get(j)!=null) ? vbmVector.get(i).get(j).toString() : "");
									break;
								case 7: thisBlock.setfontWeight((vbmVector.get(i).get(j)!=null) ? vbmVector.get(i).get(j).toString() : "");
									break;
								case 8: thisBlock.setfontFamily((vbmVector.get(i).get(j)!=null) ? vbmVector.get(i).get(j).toString() : "");
									break;
								case 9: thisBlock.setfontStyle((vbmVector.get(i).get(j)!=null) ? vbmVector.get(i).get(j).toString() : "");
									break;
								case 10: thisBlock.setcolor((vbmVector.get(i).get(j)!=null) ? vbmVector.get(i).get(j).toString() : "");
									break;
								case 11: thisBlock.setVisibility((vbmVector.get(i).get(j)!=null) ? vbmVector.get(i).get(j).toString() : "");
									break;
								case 12: thisBlock.setlineHeight((vbmVector.get(i).get(j)!=null) ? vbmVector.get(i).get(j).toString() : "");
									break;
								case 13: thisBlock.setborderColour((vbmVector.get(i).get(j)!=null) ? vbmVector.get(i).get(j).toString() : "");
									break;
								case 14: thisBlock.setborderWidth((vbmVector.get(i).get(j)!=null) ? vbmVector.get(i).get(j).toString() : "");
									break;
								case 15: thisBlock.setborderStyle((vbmVector.get(i).get(j)!=null) ? vbmVector.get(i).get(j).toString() : "");
									break;
								case 16: thisBlock.settextDecoration((vbmVector.get(i).get(j)!=null) ? vbmVector.get(i).get(j).toString() : "");
									break;
								case 17: thisBlock.setpadding((vbmVector.get(i).get(j)!=null) ? vbmVector.get(i).get(j).toString() : "");
									break;
								case 18: thisBlock.setbackground((vbmVector.get(i).get(j)!=null) ? vbmVector.get(i).get(j).toString() : "");
									break;
								case 19: thisBlock.setCSSvisibility((vbmVector.get(i).get(j)!=null) ? vbmVector.get(i).get(j).toString() : "");
									break;
								case 20: thisBlock.settextAlign((vbmVector.get(i).get(j)!=null) ? vbmVector.get(i).get(j).toString() : "");
									break;
								case 21: thisBlock.setdisplay((vbmVector.get(i).get(j)!=null) ? vbmVector.get(i).get(j).toString() : "");
									break;
								case 22: thisBlock.setclear((vbmVector.get(i).get(j)!=null) ? vbmVector.get(i).get(j).toString() : "");
									break;
								default:
									break;
								}
								
								}
							}
							
							//Make sure block is visible on the page before its added to my data structure
							if(thisBlock.getCSSvisibility()!=null)
						    {
							    if(thisBlock.getCSSvisibility().equals("visible"))
							    {
							    	thisBlock.setlookedAt(false);
							    	allVbmList.add(thisBlock);
							    }
						    }
							
						}
						
						//Get all blocks with children
						for(int k=0; k<allVbmList.size(); k++)
						{
								VisualBlock currentBlock = allVbmList.get(k);
							
									ArrayList<VisualBlock> children = getChildren(currentBlock); 
									
									if(children.size()>1){
										blocksWithChildren.put(currentBlock, children);
										currentBlock.setisBasic(false);
									}
									else if(children.size()==0) {
										currentBlock.setisBasic(true);
									}
						}
						
						//cluster by width												
						int groupCount = 0;
						for (Map.Entry<VisualBlock, ArrayList<VisualBlock>> entry : blocksWithChildren.entrySet()) 
						{
							Map.Entry<VisualBlock, ArrayList<VisualBlock>> currentBlock = entry;
							blocksWithChildren.remove(entry.getKey());

				            for (Map.Entry<VisualBlock, ArrayList<VisualBlock>> entry2 : blocksWithChildren.entrySet())
				            {  
								boolean areWidthSimilar = checkSimilarByWidth(currentBlock, entry2);
								
								if(areWidthSimilar)
								{
									//if its theere is no  group or it doesnt fit in any other
									if(allGroupsByWidth.isEmpty() || allGroupsByWidth.size() == groupCount)
									{
										ConcurrentHashMap<VisualBlock, ArrayList<VisualBlock>> newGroup = new ConcurrentHashMap<VisualBlock,ArrayList<VisualBlock>>();
										
										newGroup.put(currentBlock.getKey(), currentBlock.getValue());
										newGroup.put(entry2.getKey(), entry2.getValue());
										
										allGroupsByWidth.add(newGroup);
										blocksWithChildren.remove(entry2.getKey());
									}
									//Otherwise add to current group
									else 
									{
										allGroupsByWidth.get(groupCount).put(currentBlock.getKey(), currentBlock.getValue());
										allGroupsByWidth.get(groupCount).put(entry2.getKey(), entry2.getValue());
										blocksWithChildren.remove(entry2.getKey());
									}
								}
								
					        }
				            
				           //Only increment groupCount if a new group has been created in the iteration that just ended. 
				           if(allGroupsByWidth.size() > groupCount)
				            groupCount++;
				        }
						
						//Remove groups that have less than 3 items	
						for(int i=0;i<allGroupsByWidth.size();i++)
						{
							if(allGroupsByWidth.get(i).size() <= 3)
								allGroupsByWidth.remove(i);
						}
												
						int candidateGroupIndex =-1;

						//Check content similarity and get the candidates
						for(int j=0; j<allGroupsByWidth.size();j++)
						{
							ConcurrentHashMap<VisualBlock, ArrayList<VisualBlock>> currentGroup = allGroupsByWidth.get(j);
							int currentMatchCount = 0;
							for (Map.Entry<VisualBlock, ArrayList<VisualBlock>> myEntry : currentGroup.entrySet())
							 {
								Map.Entry<VisualBlock, ArrayList<VisualBlock>> currentEntry = myEntry;
								
								//If we have already checked this block or we matched everything break
								if(currentEntry.getKey().getlookedAt() || currentMatchCount == currentGroup.entrySet().size())
									break;

									
								
								int keyMatchCount = 0;
								int childCount = 0;
								
								
								for (Map.Entry<VisualBlock, ArrayList<VisualBlock>> myEntry1 : currentGroup.entrySet())
								 {
									childCount = getChildren(currentEntry.getKey()).size();
									if((Overlap(currentEntry.getKey(), myEntry1.getKey()) && childCount == 0))
										{
											currentGroup.remove(currentEntry.getKey());
											break;
										}
									
									//check visual similarity
									boolean areContentSimilar = checkContentSimilarity(currentEntry,myEntry1);
									
									//keep count of how many things match in the group
									if(areContentSimilar)
									{
										currentMatchCount++;
										keyMatchCount++;
										currentEntry.getKey().setmatchCoefficient(keyMatchCount);
									}
								 }
								
								//work out how similar the current key is to everything else
								double keyDegreeSim = (double) keyMatchCount /(double) currentGroup.entrySet().size();
								
								//if it's not similar enough remove it
								if(keyDegreeSim < 0.95)
									currentGroup.remove(currentEntry);
								
								currentEntry.getKey().setlookedAt(true);
							 }
							
							//work out overall group similarity degree and if it's high enough add the group to the candidates
							double degree = (double)currentMatchCount/(double)currentGroup.size();
							
							if(degree>0.9)
							{
								candidates.add(currentGroup);
							}							
						}

					
						double widest = 0;
						int highestCount = 0;
					
						//Select final candidate
						for(int k=0; k< candidates.size(); k++)
						{
							//get first block of current group to measure width
							VisualBlock currentFirstBlock = ((VisualBlock)candidates.get(k).keySet().toArray()[0]);
				
							//count hw many high level container there is
							int size = countElements(candidates.get(k));

							//if two blocks have the same number of recurring similar items pick the widest
							 if(size == highestCount)
							{
								if(currentFirstBlock.getWidth() > widest)
								{
									widest = 0;
								}
							}
							
							 //Pick block that has the highest number of recurring items and is the widest
							if((currentFirstBlock.getWidth() > widest && size >= highestCount)||size  > highestCount)
							{
								widest = currentFirstBlock.getWidth();
								highestCount = size;
								candidateGroupIndex = k;
							}
							
							
						}
			
						//get final candidate
						ConcurrentHashMap<VisualBlock, ArrayList<VisualBlock>> bestCandidate = candidates.get(candidateGroupIndex);
						
						
						
						 //drawAllGroupsContainerOnly(candidates, browser);					
						drawBestCandidate(bestCandidate, browser);
						//drawAll(allVbmList, browser);
		 
			} 
		} 
});
		 
		 shell.open ();
			while (!shell.isDisposed ()) {
				if (!display.readAndDispatch ())
					display.sleep ();
			}
			display.dispose ();
		 
	}
	
	static class CustomFunction extends BrowserFunction {
		CustomFunction (Browser browser, String name) {
			super (browser, name);
		}
		public Object function (Object[] arguments) {
						
			////////Check type of incoming message
			Object arg0 = arguments[0];
			String type = arg0.toString ();
			
			if (true){
				
				//System.out.println ("This is a block to be added to the VBM");
				Vector visualBlockVector = new Vector();				
				for (int i = 0; i < arguments.length; i++) {
					Object arg = arguments[i];
					if (arg == null) {
					} else {
						visualBlockVector.add(arg.toString ());
					}			
				}
				vbmVector.add(visualBlockVector);				
				
			} 

			Object returnValue = new Object[] {
				new Short ((short)3),
				new Boolean (true),
				null,
				new Object[] {"a string", new Boolean (false)},
				"hi",
				new Float (2.0f / 3.0f),
			};			
			return returnValue;
		}			
	}
	
static class vbm {

		static String blockDrawString(String colour, Double top, Double right, Double bottom, Double left, String contents){
			
			String drawBlock = new String();		
		
			Double seedTopInt = top;
			Double seedRightInt = right;
			Double seedBottomInt = bottom;
			Double seedLeftInt = left;
			Double seedWidthInt = seedRightInt - seedLeftInt;
			Double seedHeightInt = seedBottomInt - seedTopInt;

			String seedTop =    seedTopInt + "px";
			String seedLeft =   seedLeftInt + "px";
			String seedWidth =  seedWidthInt + "px";
			String seedHeight = seedHeightInt + "px";
			String contents2 = contents;
			
			//Create a random name for the block
			Random generator = new Random();	
			int randomInt = generator.nextInt()+1;
			long timeInMS = System.currentTimeMillis();		
			timeInMS = timeInMS + randomInt;
			String divString = "'<div id=\"" +timeInMS+ "\">"+contents2+"</div>'";									
			drawBlock = "$(" +divString+ ").appendTo(\"body\"); $(\"#" +timeInMS+ "\").each(function() {this.style.border = \"1px solid " +colour+ "\"; this.style.position = \"absolute\"; this.style.top = \"" +seedTop+ "\"; this.style.left = \"" +seedLeft+ "\"; this.style.width = \"" +seedWidth+ "\"; this.style.height = \"" +seedHeight+ "\"; $(this).css('z-index', 1000000);});";
			//System.out.println(drawBlock);
			return drawBlock; 
		}
	
		
	}


}
