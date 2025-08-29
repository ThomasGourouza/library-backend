package com.tgourouza.library_backend.mapper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tgourouza.library_backend.constant.Tag;

@Component
public class TagsMapper {

        private Map<Tag, Set<String>> tagMap = new HashMap<>();

        private final Set<String> SCIENCE_EXCLUSIONS = Set.of(
                        "science fiction",
                        "science-fiction",
                        "ciencia ficción",
                        "fantascienza");

        TagsMapper() {
                tagMap.put(Tag.LITERATURE, new HashSet<>(Arrays.asList(
                                "literature", "literary", "letters", "canonical", "classic", "classical",
                                "littérature", "littéraire", "lettres", "canonique", "classique",
                                "letteratura", "letterario", "letteraria", "canone", "canonico", "canonica", "classico",
                                "classica",
                                "literatura", "literario", "literaria", "canon", "canónico", "canónica", "clásico",
                                "clásica",
                                "literatur", "literarisch", "kanon", "kanonisch", "klassiker", "klassisch")));
                tagMap.put(Tag.PHILOSOPHY, new HashSet<>(Arrays.asList(
                                "philosophic",
                                "philosophy", "philosophical", "metaphysics", "ethics", "logic", "epistemology",
                                "philosophique", "métaphysique", "éthique", "logique", "épistémologie",
                                "filosofia", "filosofico", "filosofica", "metafisica", "etica", "logica",
                                "epistemologia",
                                "filosofía", "filosófico", "filosófica", "metafísica", "ética", "lógica",
                                "epistemología",
                                "philosophie", "philosophisch", "metaphysik", "ethik", "logik", "erkenntnistheorie")));
                tagMap.put(Tag.RELIGION, new HashSet<>(Arrays.asList(
                                "religion", "religious", "theology", "sacred", "scripture", "church", "faith",
                                "religion", "religieux", "religieuse", "théologie", "sacré", "sacrée", "écriture",
                                "écritures",
                                "église", "foi",
                                "religione", "teologia", "sacro", "sacra", "scrittura", "scritture", "chiesa",
                                "fede",
                                "religión", "religioso", "religiosa", "teología", "sagrado", "sagrada", "escritura",
                                "escrituras",
                                "iglesia", "fe",
                                "religiös", "theologie", "heilig", "schrift", "kirche", "glaube",
                                "religion", "religious", "theology", "sacred", "scripture", "scriptures",
                                "church", "faith", "belief", "worship", "prayer",
                                "jesus", "christ", "jesus christ",
                                "christian", "christians", "christianity",
                                "bible", "gospel", "new testament", "old testament", "apostle", "disciple",

                                // Français
                                "religion", "religieux", "religieuse", "théologie", "sacré", "sacrée",
                                "écriture", "écritures", "église", "foi", "croyance", "prière",
                                "jésus", "christ", "jésus-christ",
                                "chrétien", "chrétienne", "chrétiens", "chrétiennes", "christianisme",
                                "bible", "évangile", "nouveau testament", "ancien testament", "apôtre", "disciple",

                                // Italiano
                                "religione", "religioso", "religiosa", "teologia", "sacro", "sacra",
                                "scrittura", "scritture", "chiesa", "fede", "credenza", "culto", "preghiera",
                                "gesù", "cristo", "gesù cristo",
                                "cristiano", "cristiana", "cristiani", "cristiane", "cristianesimo",
                                "bibbia", "vangelo", "nuovo testamento", "antico testamento", "apostolo", "discepolo",

                                // Español
                                "religión", "religioso", "religiosa", "teología", "sagrado", "sagrada",
                                "escritura", "escrituras", "iglesia", "fe", "creencia", "culto", "oración",
                                "jesús", "cristo", "jesucristo",
                                "cristiano", "cristiana", "cristianos", "cristianas", "cristianismo",
                                "biblia", "evangelio", "nuevo testamento", "antiguo testamento", "apóstol", "discípulo",

                                // Deutsch
                                "religion", "religiös", "theologie", "heilig", "heilige",
                                "kirche", "glaube", "glauben", "anbetung", "gebet",
                                "jesus", "christus", "jesus christus",
                                "christ", "christen", "christlich", "christentum",
                                "bibel", "evangelium", "neues testament", "altes testament", "apostel", "jünger",
                                //
                                "god", "gott", "dios", "deus", "dio", "dieu")));
                tagMap.put(Tag.HISTORY, new HashSet<>(Arrays.asList(
                                "histoire", "history", "historia", "storia", "geschichte", "medieval", "medievale",
                                "médiévale",
                                "médiéval",
                                "historique", "historic", "historical", "histórico", "storico", "histórica", "storica",
                                "historisch")));
                tagMap.put(Tag.GEOGRAPHY, new HashSet<>(Arrays.asList(
                                "geography", "geographical", "cartography", "map", "mapping", "atlas",
                                "géographie", "géographique", "cartographie", "carte", "cartes", "atlas",
                                "geografia", "geografico", "geografica", "cartografia", "mappa", "mappe", "atlante",
                                "geografía", "geográfico", "geográfica", "cartografía", "mapa", "mapas", "atlas",
                                "geografie", "geographie", "geografisch", "kartografie", "karte", "karten", "atlas")));
                tagMap.put(Tag.SOCIAL, new HashSet<>(Arrays.asList(
                                "social", "society", "solidarity", "sociable",
                                "social", "société", "communauté", "solidarité", "sociable",
                                "sociale", "società", "comunità", "solidarietà", "socievole",
                                "social", "sociedad", "comunidad", "solidaridad", "sociable",
                                "sozial", "gesellschaft", "gemeinschaft", "solidarität",
                                "gesellig")));
                tagMap.put(Tag.CULTURE, new HashSet<>(Arrays.asList(
                                "culture", "cultural", "civilization", "heritage", "tradition", "custom", "folklore",
                                "artistic",
                                "culture", "culturel", "culturelle", "civilisation", "patrimoine", "tradition",
                                "coutume", "folklore",
                                "artistique",
                                "cultura", "culturale", "civiltà", "patrimonio", "tradizione", "costume", "folclore",
                                "artistico",
                                "artistica",
                                "cultura", "cultural", "civilización", "patrimonio", "tradición", "costumbre",
                                "folclore", "artístico",
                                "artística",
                                "kultur", "kulturell", "zivilisation", "kulturerbe", "tradition", "brauch", "folklore",
                                "künstlerisch")));
                tagMap.put(Tag.SOCIOLOGY, new HashSet<>(Arrays.asList(
                                "sociologie", "sociology", "sociología", "sociologia", "soziologie",
                                "sociologique", "sociologic", "sociological", "sociológico", "sociológica",
                                "sociologico",
                                "sociologica", "soziologisch",
                                // English
                                "sociology", "sociological", "social science", "societal", "sociologist",
                                // Français
                                "sociologie", "sociologique", "science sociale", "sociétal", "sociologue",
                                // Italiano
                                "sociologia", "sociologico", "sociologica", "scienza sociale", "sociologo", "sociologa",
                                // Español
                                "sociología", "sociológico", "sociológica", "ciencia social", "sociólogo", "socióloga",
                                // Deutsch
                                "soziologie", "soziologisch", "sozialwissenschaft", "gesellschaftlich", "soziologe",
                                "soziologin")));
                tagMap.put(Tag.POLITICS, new HashSet<>(Arrays.asList(
                                "politique", "politics", "político", "política", "politico", "politica", "politik",
                                "politic", "political", "politisch",
                                // English
                                "politics", "political", "politic", "policy", "government", "state", "democracy",
                                "republic",
                                "parliament",
                                // Français
                                "politique", "political", "politic", "politicien", "gouvernement", "état", "démocratie",
                                "république",
                                "parlement",
                                // Italiano
                                "politica", "politico", "politica", "politici", "governo", "stato", "democrazia",
                                "repubblica",
                                "parlamento",
                                // Español
                                "política", "político", "política", "políticos", "gobierno", "estado", "democracia",
                                "república",
                                "parlamento",
                                // Deutsch
                                "politik", "politisch", "politiker", "regierung", "staat", "demokratie", "republik",
                                "parlament",

                                // English
                                "politics", "political", "politic", "policy", "government", "state",
                                "democracy", "republic", "parliament", "diplomacy", "diplomatic", "diplomat",

                                // ---

                                // Français
                                "politique", "politicien", "gouvernement", "état",
                                "démocratie", "république", "parlement",
                                "diplomatie", "diplomatique", "diplomate",

                                // Italiano
                                "politica", "politico", "politici", "governo", "stato",
                                "democrazia", "repubblica", "parlamento",
                                "diplomazia", "diplomatico", "diplomatica", "diplomatici", "diplomatiche",
                                "diplomatico/a",

                                // Español
                                "política", "político", "política", "políticos", "gobierno", "estado",
                                "democracia", "república", "parlamento",
                                "diplomacia", "diplomático", "diplomática", "diplomáticos", "diplomáticas",
                                "diplomático/a",

                                // Deutsch
                                "politik", "politisch", "politiker", "regierung", "staat",
                                "demokratie", "republik", "parlament",
                                "diplomatie", "diplomatisch", "diplomat")));
                tagMap.put(Tag.MYTHOLOGY, new HashSet<>(Arrays.asList(
                                // English
                                "mythology", "mythological", "myth", "myths", "pantheon", "god", "gods", "hero",
                                "heroes", "deity",
                                "divinity",
                                // Français
                                "mythologie", "mythologique", "mythe", "mythes", "panthéon", "dieu", "dieux", "héros",
                                "héroïne",
                                "divinité",
                                // Italiano
                                "mitologia", "mitologico", "mitologica", "mito", "miti", "pantheon", "dio", "dei",
                                "dea", "eroe",
                                "eroi", "eroina", "divinità",
                                // Español
                                "mitología", "mitológico", "mitológica", "mito", "mitos", "panteón", "dios", "dioses",
                                "diosa", "héroe",
                                "héroes", "heroína", "divinidad",
                                // Deutsch
                                "mythologie", "mythologisch", "mythos", "mythen", "götterwelt", "gott", "götter",
                                "held", "helden",
                                "heldin", "gottheit", "divinität")));

                tagMap.put(Tag.LEGEND, new HashSet<>(Arrays.asList(
                                // English
                                "legend", "legendary", "folklore", "saga", "tale", "heroic",
                                // Français
                                "légende", "légendaire", "folklore", "saga", "conte", "héroïque",
                                // Italiano
                                "leggenda", "leggendario", "leggendaria", "folclore", "saga", "racconto", "eroico",
                                "eroica",
                                // Español
                                "leyenda", "legendario", "legendaria", "folclore", "saga", "cuento", "heroico",
                                "heroica",
                                // Deutsch
                                "legende", "legendär", "folklore", "saga", "märchen", "heldenhaft")));

                tagMap.put(Tag.PSYCHOLOGY, new HashSet<>(Arrays.asList(
                                // English
                                "psychology", "psychological", "psyche", "psychic", "cognitive", "behavior",
                                "behavioral", "mind",
                                // Français
                                "psychologie", "psychologique", "psyché", "psychique", "cognitif", "cognitive",
                                "comportement",
                                "comportemental",
                                // Italiano
                                "psicologia", "psicologico", "psicologica", "psiche", "psichico", "psichica",
                                "cognitivo", "cognitiva",
                                "comportamento", "comportamentale",
                                // Español
                                "psicología", "psicológico", "psicológica", "psique", "psíquico", "psíquica",
                                "cognitivo", "cognitiva",
                                "comportamiento", "conductual",
                                // Deutsch
                                "psychologie", "psychologisch", "psyche", "psychisch", "kognitiv", "verhalten",
                                "verhaltensbezogen")));

                tagMap.put(Tag.TECHNOLOGY, new HashSet<>(Arrays.asList(
                                // English
                                "technology", "technological", "technical", "innovation", "engineering", "machine",
                                "digital",
                                "robotics", "automation", "ai", "artificial intelligence",
                                // Français
                                "technologie", "technologique", "technique", "innovation", "ingénierie", "machine",
                                "numérique",
                                "robotique", "automatisation", "ia", "intelligence artificielle",
                                // Italiano
                                "tecnologia", "tecnologico", "tecnologica", "tecnico", "innovazione", "ingegneria",
                                "macchina",
                                "digitale", "robotica", "automazione", "ia", "intelligenza artificiale",
                                // Español
                                "tecnología", "tecnológico", "tecnológica", "técnico", "innovación", "ingeniería",
                                "máquina", "digital",
                                "robótica", "automatización", "ia", "inteligencia artificial",
                                // Deutsch
                                "technologie", "technologisch", "technisch", "innovation", "ingenieurwesen", "maschine",
                                "digital",
                                "robotik", "automatisierung", "ki", "künstliche intelligenz")));
                tagMap.put(Tag.SCIENCE, new HashSet<>(Arrays.asList(
                                "mathématiques", "mathematic", "mathematics", "matemáticas", "matematica", "mathematik",
                                "mathématique", "mathematical", "matemático", "matematico", "matemática",
                                "mathematisch", "maths",
                                "science", "ciencia", "scienza", "wissenschaft",
                                "scientifique", "scientific", "científic", "wissenschaftlich",
                                "biologie", "biology", "biología", "biologia",
                                "biologique", "biological", "biológico", "biologic", "biologico", "biológica",
                                "biologica",
                                "biologisch",
                                "chimie", "chemistry", "químico", "chimico", "química", "chimica",
                                "chimique", "chemical", "chemisch",
                                "physique", "physics", "físico", "fisico", "física", "fisica", "physik",
                                "astronomie", "astronomy", "astronomía", "astronomia", "Astronomie",
                                "astronomique", "astronomical", "astronómico", "astronómica", "astronomico",
                                "astronomica",
                                "astronomisch")));
                tagMap.put(Tag.ECONOMICS, new HashSet<>(Arrays.asList(
                                "economic", "ökonomisch",
                                // English
                                "economics", "economic", "economy", "macroeconomics", "microeconomics", "market",
                                "trade", "finance",
                                "capital",
                                // Français
                                "économie", "économique", "macroéconomie", "microéconomie", "marché", "commerce",
                                "finance", "capital",
                                // Italiano
                                "economia", "economico", "economica", "macroeconomia", "microeconomia", "mercato",
                                "commercio",
                                "finanza", "capitale",
                                // Español
                                "economía", "económico", "económica", "macroeconomía", "microeconomía", "mercado",
                                "comercio",
                                "finanzas", "capital",
                                // Deutsch
                                "ökonomie", "wirtschaft", "wirtschaftlich", "volkswirtschaft", "betriebswirtschaft",
                                "markt", "handel",
                                "finanzen", "kapital")));
                tagMap.put(Tag.ART, new HashSet<>(Arrays.asList(
                                "artiste", "artist", "artista", "künstler", "gemälde",
                                // English
                                "art", "artistic", "fine arts", "visual arts", "painting", "sculpture", "drawing",
                                "printmaking",
                                // Français
                                "art", "artistique", "beaux-arts", "arts visuels", "peinture", "sculpture", "dessin",
                                "gravure",
                                // Italiano
                                "arte", "artistico", "artistica", "belle arti", "arti visive", "pittura", "scultura",
                                "disegno",
                                "incisione",
                                // Español
                                "arte", "artístico", "artística", "bellas artes", "artes visuales", "pintura",
                                "escultura", "dibujo",
                                "grabado",
                                // Deutsch
                                "kunst", "künstlerisch", "bildende kunst", "malerei", "skulptur", "zeichnung",
                                "grafik")));
                tagMap.put(Tag.MUSIC, new HashSet<>(Arrays.asList(
                                // English
                                "music", "musical", "musician", "composer", "song", "melody", "harmony", "rhythm",
                                "orchestra",
                                "symphony", "opera",
                                // Français
                                "musique", "musical", "musicale", "musicien", "musicienne", "compositeur",
                                "compositrice", "chanson",
                                "mélodie", "harmonie", "rythme", "orchestre", "symphonie", "opéra",
                                // Italiano
                                "musica", "musicale", "musicista", "compositore", "compositrice", "canzone", "melodia",
                                "armonia",
                                "ritmo", "orchestra", "sinfonia", "opera",
                                // Español
                                "música", "musical", "músico", "música", "compositor", "compositora", "canción",
                                "melodía", "armonía",
                                "ritmo", "orquesta", "sinfonía", "ópera",
                                // Deutsch
                                "musik", "musikalisch", "musiker", "musikerin", "komponist", "komponistin", "lied",
                                "melodie",
                                "harmonie", "rhythmus", "orchester", "sinfonie", "oper")));
                tagMap.put(Tag.EDUCATION, new HashSet<>(Arrays.asList(
                                "erziehung", "bildend",
                                // English
                                "education", "educational", "school", "teaching", "learning", "pedagogy", "didactics",
                                "curriculum",
                                // Français
                                "éducation", "éducatif", "éducative", "scolaire", "enseignement", "apprentissage",
                                "pédagogie",
                                "didactique", "programme",
                                // Italiano
                                "educazione", "educativo", "educativa", "scolastico", "scolastica", "insegnamento",
                                "apprendimento",
                                "pedagogia", "didattica", "curricolo",
                                // Español
                                "educación", "educativo", "educativa", "escolar", "enseñanza", "aprendizaje",
                                "pedagogía", "didáctica",
                                "currículo",
                                // Deutsch
                                "bildung", "pädagogisch", "schulisch", "unterricht", "lernen", "pädagogik", "didaktik",
                                "lehrplan")));
                tagMap.put(Tag.ROMAN, new HashSet<>(Arrays.asList(
                                // English
                                "novel", "novels", "novelistic", "novelist",
                                // Français
                                "roman", "romans", "romanesque", "romancière", "romancier", "fiction", "nouvelle", "nouvelles",
                                // Italiano
                                "romanzo", "romanzi", "romanzesco", "romanzesca", "romanziere", "romanziera",
                                "finzione",
                                // Español
                                "novela", "novelas", "novelesco", "novelesca", "novelista", "ficción",
                                // Deutsch
                                "roman", "romane", "romanhaft", "romanautor", "romanautorin", "fiktion")));
                tagMap.put(Tag.ESSAY, new HashSet<>(Arrays.asList(
                                // English
                                "essay", "essays", "essayistic", "essayist",
                                // Français
                                "essai", "essais", "essayistique", "essayiste",
                                // Italiano
                                "saggio", "saggi", "saggistico", "saggistica", "saggista",
                                // Español
                                "ensayo", "ensayos", "ensayístico", "ensayística", "ensayista",
                                // Deutsch
                                "essay", "essays", "essayistisch", "essayist")));
                tagMap.put(Tag.POETRY, new HashSet<>(Arrays.asList(
                                "épique", "epopeya", "épico", "epopea", "epico", "episch",
                                "poetry", "poetical", "poetic", "poem", "poet",
                                "verse", "stanza", "lyric", "lyrical",
                                "sonnet", "ode", "haiku", "epic", "ballad", "elegy",
                                "poésie", "poétique", "poème", "poète",
                                "vers", "strophe", "lyrique",
                                "sonnet", "ode", "haïku", "épopée", "ballade", "élégie",
                                "poesia", "poetico", "poetica", "poema", "poeta",
                                "verso", "strofa", "lirico", "lirica",
                                "sonetto", "ode", "haiku", "epica", "ballata", "elegia",
                                "poesía", "poético", "poética", "poema", "poeta",
                                "verso", "estrofa", "lírica", "lírico", "lírica",
                                "soneto", "oda", "haiku", "épica", "balada", "elegía",
                                "poesie", "dichtung", "poetisch", "gedicht", "dichter",
                                "vers", "strophe", "lyrik", "lyrisch",
                                "sonett", "ode", "haiku", "epos", "ballade", "elegie")));
                tagMap.put(Tag.THEATER, new HashSet<>(Arrays.asList(
                                "theater", "theatre", "theatrical", "drama", "dramatic",
                                "théâtre", "théâtral", "drame", "dramatique",
                                "teatro", "teatrale", "dramma", "drammatico", "drammatica",
                                "teatro", "teatral", "drama", "dramático", "dramática",
                                "theater", "theatralisch", "drama", "dramatisch",
                                "play", "playwright",
                                "tragedy", "tragic",
                                "pièce", "pièce de théâtre", "auteur dramatique",
                                "tragédie", "tragique",
                                "opera teatrale", "commediografo",
                                "tragedia", "tragico", "tragica",
                                "obra de teatro", "dramaturgo",
                                "tragedia", "trágico", "trágica",
                                "theaterstück", "dramatiker",
                                "tragödie", "tragisch")));
                tagMap.put(Tag.BIOGRAPHY, new HashSet<>(Arrays.asList(
                                "biographie", "biographique", "biography", "biographical", "biografía", "biográfico",
                                "biografia",
                                "biografico", "biografie",
                                "biografisch",
                                "mémoire", "mémorial", "memoir", "memoria", "memorial", "memoriale", "memoiren",
                                "journal intime", "diary", "diario", "diarístico", "diaristico", "tagebuch",
                                "journal", "journalistique", "journalistic", "periódico", "periodístico", "giornale",
                                "giornalistico",
                                "zeitung", "journalistisch")));
                tagMap.put(Tag.LETTERS, new HashSet<>(Arrays.asList(
                                "lettres", "épistolaire", "letters", "epistolary", "cartas", "epistolar", "lettere",
                                "epistolare",
                                "briefe", "epistolarisch",
                                "lettre", "letter", "carta", "lettera", "brief")));
                tagMap.put(Tag.TALE, new HashSet<>(Arrays.asList(
                                "conte", "contesque", "tale", "cuento", "racconto", "erzählung", "märchenhaft",
                                "conte de fées", "féerique", "fairy tale", "cuento de hadas", "de hadas", "fiaba",
                                "fiabesco",
                                "märchen",
                                "fable", "fabuleux", "fabular", "fábula", "fabuloso", "favola", "favolistico", "fabel",
                                "fabelhaft")));
                tagMap.put(Tag.ENCYCLOPEDIA, new HashSet<>(Arrays.asList(
                                "encyclopédie", "encyclopédique", "encyclopedia", "encyclopedic", "enciclopedia",
                                "enciclopédico",
                                "enciclopedico", "enzyklopädie",
                                "enzyklopädisch",
                                "atlas", "cartographique", "cartographic", "cartográfico", "atlante", "cartografico",
                                "kartographisch",
                                "dictionnaire", "lexicographique", "dictionary", "lexicographic", "diccionario",
                                "lexicográfico",
                                "dizionario", "lessicografico",
                                "wörterbuch", "lexikographisch")));
                tagMap.put(Tag.CLASSIC, new HashSet<>(Arrays.asList(
                                "classic", "classical", "canon", "canonical",
                                "classique", "canon", "canonique",
                                "classico", "classica", "canonico", "canonica",
                                "clásico", "clásica", "canónico", "canónica",
                                "klassisch", "klassiker", "kanonisch")));

                tagMap.put(Tag.SCIENCE_FICTION, new HashSet<>(Arrays.asList(
                                "science fiction", "sci-fi", "sf", "dystopia", "dystopian",
                                "utopia", "utopian", "cyberpunk", "space opera", "time travel",
                                "post-apocalyptic", "apocalyptic", "speculative fiction",
                                "science-fiction", "sf", "dystopie", "dystopique",
                                "utopie", "utopique", "cyberpunk", "space opera", "opéra spatial",
                                "voyage dans le temps", "post-apocalyptique", "apocalyptique",
                                "fiction spéculative",
                                "fantascienza", "distopia", "distopico", "distopica",
                                "utopia", "utopico", "utopica", "cyberpunk", "space opera",
                                "opera spaziale", "viaggio nel tempo",
                                "post-apocalittico", "post-apocalittica",
                                "apocalittico", "apocalittica",
                                "narrativa speculativa",
                                "ciencia ficción", "distopía", "distópico", "distópica",
                                "utopía", "utópico", "utópica", "ciberpunk",
                                "space opera", "ópera espacial", "viaje en el tiempo",
                                "postapocalíptico", "postapocalíptica",
                                "apocalíptico", "apocalíptica",
                                "ficción especulativa",
                                "science-fiction", "sci-fi", "sf", "dystopie", "dystopisch",
                                "utopie", "utopisch", "cyberpunk", "space opera", "weltraumoper",
                                "zeitreise", "postapokalyptisch", "apokalyptisch",
                                "spekulative fiktion")));
                tagMap.put(Tag.CHILDREN, new HashSet<>(Arrays.asList(
                                // English
                                "children", "child", "kid", "kids", "childhood", "juvenile", "youth", "toddler",
                                "infant",
                                // Français
                                "enfant", "enfants", "enfance", "jeunesse", "jeune",
                                // Italiano
                                "bambino", "bambini", "bambina", "bambine", "infanzia",
                                "ragazza", "piccolo", "piccola",
                                // Español
                                "niño", "niños", "niña", "niñas", "infancia", "juventud",
                                // Deutsch
                                "kind", "kinder", "kindheit", "jugend", "jugendlich", "junge")));
        }

        public Set<Tag> fromSet(Set<String> tags) {
                if (tags == null || tags.isEmpty())
                        return Set.of();

                // normalize input phrases
                Set<String> normalized = tags.stream()
                                .filter(Objects::nonNull)
                                .map(t -> t.trim().toLowerCase())
                                .filter(s -> !s.isEmpty())
                                .collect(Collectors.toSet());

                // collect tags
                Set<Tag> result = tagMap.entrySet().stream()
                                .filter(entry -> normalized.stream().anyMatch(
                                                phrase -> entry.getValue().stream().anyMatch(
                                                                keyword -> containsWholeWord(phrase, keyword))))
                                .map(Map.Entry::getKey)
                                .collect(Collectors.toSet());

                // refine science rule
                if (result.contains(Tag.SCIENCE)) {
                        boolean hasExclusion = normalized.stream()
                                        .anyMatch(SCIENCE_EXCLUSIONS::contains);

                        boolean hasPureScience = normalized.stream().anyMatch(
                                        phrase -> tagMap.get(Tag.SCIENCE).stream()
                                                        .anyMatch(keyword -> containsWholeWord(phrase, keyword)));

                        // Remove SCIENCE only if an exclusion exists AND there is no standalone
                        // "science"
                        if (hasExclusion && !hasPureScience) {
                                result.remove(Tag.SCIENCE);
                        }
                }

                return result;
        }

        /** true if `keyword` appears as a full word in `phrase` */
        private boolean containsWholeWord(String phrase, String keyword) {
                if (keyword == null || keyword.isBlank())
                        return false;
                Pattern p = Pattern.compile("\\b" + Pattern.quote(keyword.toLowerCase()) + "\\b",
                                Pattern.UNICODE_CHARACTER_CLASS);
                return p.matcher(phrase).find();
        }
}
