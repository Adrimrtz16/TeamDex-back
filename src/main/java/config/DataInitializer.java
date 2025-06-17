package config;

import entity.Team;
import entity.User;
import enums.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import repository.TeamRepository;
import repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, TeamRepository teamRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Inicializa datos de prueba al arrancar la aplicación
    @Override
    public void run(String... args) {
        if (userRepository.findByUsername("WolfieGlick").isEmpty()) {
            User user1 = new User();
            user1.setUsername("WolfieGlick");
            user1.setPassword(passwordEncoder.encode("password"));
            user1.setEmail("WolfieGlick@poke.com");
            user1.setRole(Role.USER);
            user1.setBio("¡Quiero ser un Maestro Pokémon!");
            user1.setProfilePictureUrl("https://adrimrtz16.github.io/profilePics/images/cheren-gen5bw2.png");
            userRepository.save(user1);

            Team team1 = new Team(null,
                    "Farigiraf @ Safety Goggles  Ability: Armor Tail  Level: 50  Tera Type: Water  EVs: 228 HP / 100 Def / 180 SpD  Sassy Nature  IVs: 0 Spe  - Psychic Fangs  - Ally Switch  - Trick Room  - Helping Hand",
                    "Ditto @ Focus Sash  Ability: Imposter  Level: 50  Tera Type: Ghost  EVs: 252 HP / 252 Def / 4 SpD  Relaxed Nature  IVs: 30 Atk / 0 Spe  - Transform",
                    "Smeargle @ Eject Pack  Ability: Moody  Level: 50  Tera Type: Normal  EVs: 252 HP / 252 Def / 4 SpD  Relaxed Nature  IVs: 0 Spe  - Follow Me  - Spore  - Fake Out  - Wide Guard",
                    "Kyogre @ Choice Specs  Ability: Drizzle  Level: 50  Tera Type: Water  EVs: 252 HP / 4 Atk / 252 SpA  Quiet Nature  IVs: 0 Spe  - Origin Pulse  - Water Spout  - Ice Beam  - Tera Blast",
                    "Landorus @ Life Orb  Ability: Sand Force  Level: 50  Tera Type: Grass  EVs: 252 HP / 252 SpA / 4 SpD  Quiet Nature  IVs: 0 Atk / 0 Spe  - Earth Power  - Sludge Bomb  - Sandsear Storm  - Protect",
                    "Urshifu @ Choice Scarf  Ability: Unseen Fist  Level: 50  Tera Type: Fighting  EVs: 252 Atk / 4 SpD / 252 Spe  Jolly Nature  - Close Combat  - Sucker Punch  - Crunch  - Double-Edge",
                    "Equipo Wolfie",
                    user1);
            teamRepository.save(team1);
        }

        if (userRepository.findByUsername("ash").isEmpty()) {
            User user2 = new User();
            user2.setUsername("Folagor03");
            user2.setPassword(passwordEncoder.encode("password"));
            user2.setEmail("Folagor03@poke.com");
            user2.setRole(Role.USER);
            user2.setBio("¡Wolfie, te ganaré!");
            user2.setProfilePictureUrl("https://adrimrtz16.github.io/profilePics/images/clemont.png");
            userRepository.save(user2);

            // Primer equipo
            Team team2 = new Team(null,
                    "Miraidon @ Choice Specs  Ability: Hadron Engine  Level: 50  Tera Type: Fairy  EVs: 4 HP / 252 SpA / 252 Spe  Timid Nature  - Electro Drift  - Draco Meteor  - Volt Switch  - Dazzling Gleam",
                    "Whimsicott @ Covert Cloak  Ability: Prankster  Level: 79  Tera Type: Dark  EVs: 156 HP / 4 Def / 4 SpA / 116 SpD / 228 Spe  Timid Nature  IVs: 0 Atk  - Tailwind  - Encore  - Light Screen  - Moonblast",
                    "Farigiraf @ Electric Seed  Ability: Armor Tail  Level: 91  Tera Type: Dark  EVs: 252 HP / 76 Def / 4 SpA / 132 SpD / 44 Spe  Bold Nature  IVs: 0 Atk  - Hyper Voice  - Foul Play  - Helping Hand  - Trick Room",
                    "Iron Hands @ Assault Vest  Ability: Quark Drive  Level: 50  Tera Type: Water  EVs: 84 HP / 252 Atk / 172 SpD  Brave Nature  IVs: 0 Spe  - Low Kick  - Drain Punch  - Wild Charge  - Fake Out",
                    "Calyrex-Shadow @ Focus Sash  Ability: As One (Spectrier)  Level: 50  Tera Type: Ghost  EVs: 4 HP / 252 SpA / 252 Spe  Timid Nature  IVs: 0 Atk  - Astral Barrage  - Expanding Force  - Pollen Puff  - Protect",
                    "Chi-Yu @ Choice Scarf  Ability: Beads of Ruin  Level: 50  Tera Type: Ghost  EVs: 20 HP / 252 SpA / 236 Spe  Modest Nature  IVs: 0 Atk  - Snarl  - Overheat  - Dark Pulse  - Heat Wave",
                    "Equipo Fola",
                    user2);
            teamRepository.save(team2);

            // Segundo equipo
            Team team3 = new Team(null,
                    "Grimmsnarl @ Light Clay  Ability: Prankster  Level: 50  Tera Type: Ghost  EVs: 252 HP / 164 Def / 92 SpD  Impish Nature  - Spirit Break  - Reflect  - Light Screen  - Thunder Wave",
                    "Amoonguss @ Rocky Helmet  Ability: Regenerator  Level: 50  Tera Type: Water  EVs: 236 HP / 196 Def / 76 SpD  Calm Nature  IVs: 0 Spe  - Protect  - Spore  - Rage Powder  - Pollen Puff",
                    "Incineroar @ Safety Goggles  Ability: Intimidate  Level: 50  Tera Type: Ghost  EVs: 252 HP / 4 Atk / 116 Def / 84 SpD / 52 Spe  Impish Nature  - Knock Off  - Will-O-Wisp  - Fake Out  - Parting Shot",
                    "Kyogre @ Leftovers  Ability: Drizzle  Level: 50  Tera Type: Grass  EVs: 244 HP / 148 Def / 4 SpA / 4 SpD / 108 Spe  Modest Nature  - Origin Pulse  - Ice Beam  - Protect  - Calm Mind",
                    "Rillaboom @ Assault Vest  Ability: Grassy Surge  Level: 50  Tera Type: Fire  EVs: 244 HP / 36 Atk / 12 Def / 76 SpD / 140 Spe  Adamant Nature  - Grassy Glide  - Wood Hammer  - Fake Out  - U-turn",
                    "Raging Bolt @ Booster Energy  Ability: Protosynthesis  Level: 50  Tera Type: Electric  EVs: 68 HP / 4 Def / 180 SpA / 4 SpD / 252 Spe  Modest Nature  IVs: 20 Atk  - Thunderclap  - Draco Meteor  - Thunderbolt  - Protect",
                    "Equipo Fola 2",
                    user2);
            teamRepository.save(team3);

            // Tercer equipo
            Team team4 = new Team(null,
                    "Calyrex-Shadow @ Life Orb  Ability: As One (Spectrier)  Level: 50  Tera Type: Fairy  EVs: 108 HP / 108 Def / 36 SpA / 4 SpD / 252 Spe  Timid Nature  IVs: 0 Atk  - Astral Barrage  - Calm Mind  - Protect  - Draining Kiss",
                    "Incineroar @ Rocky Helmet  Ability: Intimidate  Level: 50  Tera Type: Ghost  EVs: 252 HP / 4 Atk / 84 Def / 92 SpD / 76 Spe  Impish Nature  - Fake Out  - Taunt  - Knock Off  - Parting Shot",
                    "Rillaboom @ Clear Amulet  Ability: Grassy Surge  Level: 50  Tera Type: Fire  EVs: 140 HP / 116 Atk / 4 Def / 124 SpD / 124 Spe  Adamant Nature  - Fake Out  - Wood Hammer  - Grassy Glide  - U-turn",
                    "Urshifu-Rapid-Strike @ Focus Sash  Ability: Unseen Fist  Level: 50  Tera Type: Stellar  EVs: 4 HP / 252 Atk / 252 Spe  Adamant Nature  - Surging Strikes  - Close Combat  - Aqua Jet  - Detect",
                    "Raging Bolt @ Assault Vest  Ability: Protosynthesis  Level: 50  Tera Type: Electric  EVs: 244 HP / 12 Def / 140 SpA / 68 SpD / 44 Spe  Modest Nature  IVs: 20 Atk  - Electroweb  - Draco Meteor  - Thunderclap  - Volt Switch",
                    "Clefairy @ Eviolite  Ability: Friend Guard  Level: 50  Tera Type: Ground  EVs: 252 HP / 212 Def / 44 SpD  Bold Nature  IVs: 0 Atk / 17 Spe  - Follow Me  - Helping Hand  - Protect  - Sing",
                    "Equipo Fola 3",
                    user2);
            teamRepository.save(team4);

            // Cuarto equipo
            Team team5 = new Team(null,
                    "Incineroar @ Sitrus Berry  Ability: Intimidate  Level: 50  Tera Type: Ghost  EVs: 252 HP / 4 Atk / 84 Def / 156 SpD / 4 Spe  Impish Nature  - Fake Out  - Knock Off  - Will-O-Wisp  - Parting Shot",
                    "Miraidon @ Electric Seed  Ability: Hadron Engine  Level: 50  Tera Type: Fairy  EVs: 252 HP / 108 Def / 36 SpA / 4 SpD / 108 Spe  Modest Nature  - Dragon Pulse  - Parabolic Charge  - Protect  - Calm Mind",
                    "Ogerpon-Cornerstone @ Cornerstone Mask  Ability: Sturdy  Level: 50  Tera Type: Rock  EVs: 4 HP / 252 Atk / 252 Spe  Jolly Nature  IVs: 20 SpA  - Ivy Cudgel  - Power Whip  - Follow Me  - Spiky Shield",
                    "Oranguru @ Covert Cloak  Ability: Symbiosis  Level: 50  Tera Type: Ground  EVs: 236 HP / 20 SpD / 252 Spe  Timid Nature  IVs: 0 Atk  - Instruct  - Scary Face  - Bulldoze  - Taunt",
                    "Tinkaton @ Assault Vest  Ability: Mold Breaker  Level: 50  Tera Type: Fire  EVs: 236 HP / 100 Atk / 52 Def / 44 SpD / 76 Spe  Careful Nature  - Fake Out  - Gigaton Hammer  - Play Rough  - Feint",
                    "Thundurus @ Safety Goggles  Ability: Prankster  Level: 50  Tera Type: Ghost  EVs: 252 HP / 84 Def / 172 SpD  Calm Nature  IVs: 14 Atk  - Thunder Wave  - Eerie Impulse  - Taunt  - Sludge Bomb",
                    "Equipo Fola 4",
                    user2);
            teamRepository.save(team5);
        }

        String[] usernames = {
                "LunaStars", "RayoSolar", "NubeMistral", "AquaMarina", "FuegoBrillante",
                "TierraFirme", "ElectroVioleta", "VientoLibre", "NocheEstelar", "BosqueVerde",
                "RojoPasion", "OceanoProfundo", "MontanaAlta", "NeblinaGris", "TruenoAzul",
                "AuroraCarmesí", "CentellaVerde", "CoralDorado", "LuzNocturna", "HojaNueva",
                "TempestadOscura", "FlamaVeloz", "EcoSilvestre", "CristalLunar", "AlmaErrante"
        };

        String[] emails = {
                "LunaStars@poke.com", "RayoSolar@poke.com", "NubeMistral@poke.com", "AquaMarina@poke.com",
                "FuegoBrillante@poke.com", "TierraFirme@poke.com", "ElectroVioleta@poke.com", "VientoLibre@poke.com",
                "NocheEstelar@poke.com", "BosqueVerde@poke.com", "RojoPasion@poke.com", "OceanoProfundo@poke.com",
                "MontanaAlta@poke.com", "NeblinaGris@poke.com", "TruenoAzul@poke.com", "AuroraCarmesi@poke.com",
                "CentellaVerde@poke.com", "CoralDorado@poke.com", "LuzNocturna@poke.com", "HojaNueva@poke.com",
                "TempestadOscura@poke.com", "FlamaVeloz@poke.com", "EcoSilvestre@poke.com", "CristalLunar@poke.com",
                "AlmaErrante@poke.com"
        };

        String[] bios = {
                "¡Entrenador en ciernes!", "Buscando mi próximo combate", "Fan del viento", "Amante del agua",
                "Vivo para la batalla", "Conectado con la tierra", "Descubriendo la electricidad", "Libre como el viento",
                "Bajo las estrellas", "Protector del bosque", "Pasión por el rojo", "Explorador oceánico", "Ascendiendo montañas",
                "Perdido entre la neblina", "¡El trueno no me alcanza!", "Brillo como una aurora", "Rápido como una centella",
                "Coral y calma", "Siempre alerta en la noche", "Reverdeciendo esperanzas", "Desde la tormenta",
                "La llama siempre encendida", "Soy uno con la naturaleza", "Misterioso como la luna", "Errante pero fuerte"
        };

        String[] pics = {
                "akari.png", "blue-gen7.png", "brassius.png", "brendan-e.png", "briar.png",
                "brock-lgpe.png", "bugcatcher-gen4dp.png", "calem.png", "carmine.png", "chili.png",
                "cilan.png", "clavell-s.png", "clay.png", "cynthia-gen7.png", "diantha.png",
                "ethan.png", "fantina.png", "flannery-gen6.png", "giovanni-lgpe.png", "guzma.png",
                "hau.png", "hilda.png", "iono.png", "korrina.png", "leon.png"
        };

        for (int i = 0; i < usernames.length; i++) {
            if (userRepository.findByUsername(usernames[i]).isEmpty()) {
                User u = new User();
                u.setUsername(usernames[i]);
                u.setPassword(passwordEncoder.encode("password"));
                u.setEmail(emails[i]);
                u.setRole(Role.USER);
                u.setBio(bios[i]);
                u.setProfilePictureUrl("https://adrimrtz16.github.io/profilePics/images/" + pics[i]);
                userRepository.save(u);
            }
        }
    }
}