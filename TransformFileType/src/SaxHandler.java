/*
 * @authour Gongsheng Yuan
 */
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import opennlp.tools.stemmer.snowball.SnowballStemmer;

public class SaxHandler extends DefaultHandler{

    int fileNumber = 0;                                 // count how many files we produce
    Stack<String> stack = new Stack<String>();    // for element match, so we need a stack(Judging a complete xml paragraph when it finish)

    private List<String> final_contents = null;
    private List<String> temp_contents = null;

    /*
        Begin to parse xml file
     */
    @Override
    public void startDocument() throws SAXException {
        super.startDocument();

        System.out.println("开始解析xml文件");
        final_contents = new ArrayList<String>();
    }

    /*
            Parsing xml file is over.
         */
    @Override
    public void endDocument() throws SAXException {
        super.endDocument();

        System.out.println("结束解析xml文件");
    }

    /*
    *   Begin to parse xml element
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        stack.push(qName);


        /* ***************************************************************************************************
         */
//        //如果不知道标签的属性，则需要遍历Attributes分别获取属性名与属性值
//        int len = attributes.getLength();
//
//        for(int i = 0; i < len; i++) {
//            //通过Attributes.getQName(int index)获取属性名称
//            System.out.print("获取book属性" + attributes.getQName(i));
//            //通过Attributes.getValue(int index)获取属性值
//            System.out.println("，属性值-->" + attributes.getValue(i));
//        }


  /* ***************************************************************************************************
   */
//        tag = qName;
//        if (lookfor.equals(tag)) {
//            temp_contents = new ArrayList<String>();
//        }
    }

    /*
     *   Parsing xml element is over.
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);

        if (stack.peek().equals(qName)){
            stack.pop();
            if (stack.isEmpty()){   // When stack is empty, we can get a complete part of XML.
                //输出文本，清空列表
            }
        }else {
            System.out.println("There is something wrong about element");
        }


//        if ( temp_contents != null ){  把列表里的元素变成字符串
//            StringBuilder result = new StringBuilder();
//            for (String str : temp_contents){
//                result.append(str);
//                result.append(" ");
//            }
//            final_contents.add(result.toString());
//            temp_contents = null;
//        }
//
//        tag = null;

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);

        String str = new String(ch, start, length);

        // We use stemAndClean to clean and stem word.
        str = cleanAndStem(str);



//        if (lookfor.equals(tag)) {
//            String str = new String(ch, start, length);  //如果是我们想要找的内容
//            temp_contents.add(str);
//        }
    }

    private String cleanAndStem(String str) {

        SnowballStemmer stemmer = new SnowballStemmer(SnowballStemmer.ALGORITHM.ENGLISH);

        //\\p{Alnum} means digital or alphabetic character  (Regular expression in Java)
        //[^abc] means any character except a, b and c.
        String clean = str.toLowerCase().replaceAll("[^\\p{Alnum}]", " ").replaceAll("\\s\\s+", " ");

        String[] words = clean.split("\\s+");

        clean = "";
        for (String w : words){
            clean += stemmer.stem(w) + " ";
        }

        return clean.trim();
    }


}
