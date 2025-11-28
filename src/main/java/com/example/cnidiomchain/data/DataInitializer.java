package com.example.cnidiomchain.data;

import com.example.cnidiomchain.entity.Idiom;
import com.example.cnidiomchain.repository.IdiomRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final IdiomRepository idiomRepository;

    public DataInitializer(IdiomRepository idiomRepository) {
        this.idiomRepository = idiomRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // 检查数据库中是否已经有成语数据
        if (idiomRepository.count() == 0) {
            // 初始化一些常用成语数据
            List<Idiom> idioms = new ArrayList<>();

            // 添加成语数据
            idioms.add(createIdiom("一帆风顺", "yī fān fēng shùn", "船挂着满帆顺风行驶。比喻非常顺利，没有任何阻碍。", "他这次考试真是一帆风顺，所有题目都会做。"));
            idioms.add(createIdiom("顺手牵羊", "shùn shǒu qiān yáng", "顺手把人家的羊牵走。比喻趁势将敌手捉住或乘机利用别人。现比喻乘机拿走别人的东西。", "他路过商店时顺手牵羊拿走了一个苹果。"));
            idioms.add(createIdiom("羊肠小道", "yáng cháng xiǎo dào", "曲折而极窄的路（多指山路）。", "我们沿着羊肠小道登上了山顶。"));
            idioms.add(createIdiom("道听途说", "dào tīng tú shuō", "路上听来的、路上传播的话。泛指没有根据的传闻。", "不要轻易相信道听途说的消息。"));
            idioms.add(createIdiom("说三道四", "shuō sān dào sì", "形容不负责任地胡乱议论。", "她总是喜欢在背后说三道四。"));
            idioms.add(createIdiom("四面楚歌", "sì miàn chǔ gē", "比喻陷入四面受敌、孤立无援的境地。", "公司现在面临四面楚歌的困境。"));
            idioms.add(createIdiom("歌舞升平", "gē wǔ shēng píng", "边歌边舞，庆祝太平。有粉饰太平的意思。", "在这歌舞升平的年代，我们更要保持清醒的头脑。"));
            idioms.add(createIdiom("平心静气", "píng xīn jìng qì", "心情平和，态度冷静。", "我们需要平心静气地讨论这个问题。"));
            idioms.add(createIdiom("气宇轩昂", "qì yǔ xuān áng", "形容人精力充沛，风度不凡。", "他气宇轩昂地走进了会议室。"));
            idioms.add(createIdiom("昂首挺胸", "áng shǒu tǐng xiōng", "抬起头，挺起胸膛。形容斗志高，士气旺。", "战士们昂首挺胸地走过天安门广场。"));
            idioms.add(createIdiom("胸有成竹", "xiōng yǒu chéng zhú", "原指画竹子要在心里有一幅竹子的形象。后比喻在做事之前已经拿定主意。", "他对这次比赛胸有成竹。"));
            idioms.add(createIdiom("竹报平安", "zhú bào píng ān", "比喻平安家信。", "收到你的来信，就像竹报平安一样，让我放心了。"));
            idioms.add(createIdiom("安步当车", "ān bù dàng chē", "以从容的步行代替乘车。", "我喜欢安步当车，这样既环保又健康。"));
            idioms.add(createIdiom("车水马龙", "chē shuǐ mǎ lóng", "车象流水，马象游龙。形容来往车马很多，连续不断的热闹情景。", "节日期间，大街上车水马龙，非常热闹。"));
            idioms.add(createIdiom("龙马精神", "lóng mǎ jīng shén", "比喻人精神旺盛。", "爷爷虽然年纪大了，但仍然龙马精神。"));
            idioms.add(createIdiom("神采飞扬", "shén cǎi fēi yáng", "形容兴奋得意，精神焕发的样子。", "他神采飞扬地讲述着自己的经历。"));
            idioms.add(createIdiom("扬眉吐气", "yáng méi tǔ qì", "扬起眉头，吐出怨气。形容摆脱了长期受压状态后高兴痛快的样子。", "中国运动员在奥运会上取得了好成绩，全国人民都扬眉吐气。"));
            idioms.add(createIdiom("气吞山河", "qì tūn shān hé", "气势可以吞没山河。形容气魄很大。", "他的演讲气吞山河，令人震撼。"));
            idioms.add(createIdiom("河清海晏", "hé qīng hǎi yàn", "黄河水清了，大海没有浪了。比喻天下太平。", "我们希望看到河清海晏的局面。"));
            idioms.add(createIdiom("晏然自若", "yàn rán zì ruò", "形容在紧张状态下沉静如常。", "面对危险，他晏然自若，毫不慌张。"));

            // 保存成语数据到数据库
            idiomRepository.saveAll(idioms);

            System.out.println("成语数据初始化完成，共添加了 " + idioms.size() + " 个成语。");
        } else {
            System.out.println("数据库中已有成语数据，无需初始化。");
        }
    }

    // 创建成语对象
    private Idiom createIdiom(String name, String pinyin, String meaning, String example) {
        Idiom idiom = new Idiom();
        idiom.setName(name);
        idiom.setPinyin(pinyin);
        idiom.setMeaning(meaning);
        idiom.setExample(example);
        return idiom;
    }
}