<?php

namespace ReparationBundle\Form;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class PiecesdefectueusesType extends AbstractType
{
    /**
     * {@inheritdoc}
     */
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder->add('nom')
            ->add('categorie',ChoiceType::class,['choices'=>[
                ''=>'',
                'Hunting'=>'Hunting',
                'Fishing'=>'Fishing',
            ],
            ])
            ->add('description')
            ->add('image', FileType::class, array('label' => 'Photo (png, jpeg)'))
            ->add('Send',SubmitType::class);
    }/**
     * {@inheritdoc}
     */
    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults(array(
            'data_class' => 'ReparationBundle\Entity\Piecesdefectueuses'
        ));
    }

    /**
     * {@inheritdoc}
     */
    public function getBlockPrefix()
    {
        return 'reparationbundle_piecesdefectueuses';
    }


}
