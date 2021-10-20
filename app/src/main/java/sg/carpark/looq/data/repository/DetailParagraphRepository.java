package sg.carpark.looq.data.repository;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import sg.carpark.looq.data.model.Detail;
import sg.carpark.looq.data.model.Featured;


/**
 * Created by TED on 07-Sep-20
 */
public class DetailParagraphRepository {
    private static DetailParagraphRepository instance;
    private Context context;

    public DetailParagraphRepository(Context context) {
        this.context = context;
//        customerDao = AppDatabase.getInstance().customerDao();
    }

    public static DetailParagraphRepository getInstance(Context context) {
        if (instance == null) {
            instance = new DetailParagraphRepository(context);
        }

        return instance;
    }

    public static List<Detail> generateItems(Featured selected) {
        List<Detail> listDummyData  = new ArrayList<>();
        if(!selected.getTitle().equals("false")) {
            listDummyData.add(new Detail(selected.getTitle()));
        }
        if(!selected.getDescription().equals("false")) {
            listDummyData.add(new Detail(selected.getDescription()));
        }

//        listDummyData.add(new Detail("Water. Earth. Fire. Air.\n" +
//                "Long ago, the four nations lived together in harmony. Then everything changed when the Fire Nation attacked."));
//        listDummyData.add(new Detail("Only the Avatar, master of all four elements , could stop them. But when the world needed him most, he vanished."));
//        listDummyData.add(new Detail("A hundred years passed and my brother and I discovered the new Avatar, an airbender named Aang, and although his airbending skills are great, he still has a lot to learn before he's ready to save anyone."));
//
//        listDummyData.add(new Detail("<b>What is Lorem Ipsum?</b>"));
//
//        listDummyData.add(new Detail("<b>Lorem Ipsum</b> is simply dummy text of the printing and " +
//                "typesetting industry. Lorem Ipsum has been the industry's standard dummy text " +
//                "ever since the 1500s, when an unknown printer took a galley of type and scrambled " +
//                "it to make a type specimen book. It has survived not only five centuries, but also " +
//                "the leap into electronic typesetting, remaining essentially unchanged. It was " +
//                "popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum" +
//                " passages, and more recently with desktop publishing software like Aldus PageMaker " +
//                "including versions of Lorem Ipsum."));
//
//        listDummyData.add(new Detail("<b>Why do we use it?</b>"));
//
//        listDummyData.add(new Detail("It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like)."));
//
//        listDummyData.add(new Detail("<b>Where does it come from?</b>"));
//
//        listDummyData.add(new Detail("Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32."));
//
//        listDummyData.add(new Detail("<b>Where can I get some?</b>"));
//
//        listDummyData.add(new Detail("There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc."));

        return listDummyData;
    }

}
