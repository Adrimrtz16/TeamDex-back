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
        }
    }
}