<?php

/*
 * This file is part of the FOSUserBundle package.
 *
 * (c) FriendsOfSymfony <http://friendsofsymfony.github.com/>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

namespace FOS\UserBundle\Form\Type;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\EmailType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\PasswordType;
use Symfony\Component\Form\Extension\Core\Type\RepeatedType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\HttpFoundation\File\File;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Vich\UploaderBundle\Form\Type\VichFileType;

class RegistrationFormType extends AbstractType
{
    /**
     * @var string
     */
    private $class;

    /**
     * @param string $class The User class name
     */
    public function __construct($class)
    {
        $this->class = $class;
    }

    /**
     * {@inheritdoc}
     */
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder->add('firstName',TextType::class ,array('label' => 'FirstName ') )
            ->add('lastName',TextType::class,array('label' => 'LastName ') )
            ->add('address',TextType::class,array('label' => 'Address ') )
            ->add('phoneNumber',TextType::class,array('label' => 'Phone Number ') )
            ->add('gender',ChoiceType::class,[
                'choices' => [
                    'Male'=>true,
                    'Female'=>false,
                ],
                'expanded' => true,  // => boutons
                'multiple' => false,
                'label' => 'Gender',
                'data'=>true,
            ])
            ->add('picture', FileType::class, array('label' => 'Photo (png, jpeg)'))
            ->add('roles', ChoiceType::class, array('label' => 'Type ',
                'choices' => array(
                    'TRAINER' => 'ROLE_TRAINER',
                    'REPAIRER' => 'ROLE_REPAIRER',
                    'CLIENT' => 'ROLE_CLIENT'),
                'required' => true, 'multiple' => true,))
            ->add('contractFile', VichFileType::class, [
                'required' => false,
                'allow_delete' => true,

            ])
            ->add('email', EmailType::class, array('label' => 'Email', 'translation_domain' => 'FOSUserBundle'))
            ->add('username', null, array('label' => 'Username', 'translation_domain' => 'FOSUserBundle'))
            ->add('plainPassword', RepeatedType::class, array(
                'type' => PasswordType::class,
                'options' => array(
                    'translation_domain' => 'FOSUserBundle',
                    'attr' => array(
                        'autocomplete' => 'new-password',
                    ),
                ),
                'first_options' => array('label' => 'Password'),
                'second_options' => array('label' => 'Confirm Password'),
                'invalid_message' => 'Password mismatch',
            ))
        ;
    }

    /**
     * {@inheritdoc}
     */
    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults(array(
            'data_class' => $this->class,
            'csrf_token_id' => 'registration',
        ));
    }

    // BC for SF < 3.0

    /**
     * {@inheritdoc}
     */
    public function getName()
    {
        return $this->getBlockPrefix();
    }

    /**
     * {@inheritdoc}
     */
    public function getBlockPrefix()
    {
        return 'fos_user_registration';
    }
}
